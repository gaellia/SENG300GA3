package main;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Main 
{
	public Main(File directory)
	{
		List<File> javaFiles = new ArrayList<File>(); // List of all .java files found
		
		if (directory.isDirectory()) { // if dir points to an existing directory...
			// FIRST: recursively extract all jars in this directory to TEMP folders in same directory as the corresponding jar file
			try {
				JarHandler.extractJars(directory);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			
			// SECOND: recursively search through directory (including TEMP folders) to retrieve all .java files and put them in the list
			searchFiles(directory, javaFiles);
		}
		else if (directory.isFile() && directory.getName().endsWith(".jar")) { // if dir points to an existing .jar file...
			// FIRST: extract this jar file and any nested jars to TEMP folders in same directory as the corresponding jar file
			try {
				JarHandler.extractJars(directory);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			
			// SECOND: recursively search through jar directory (including TEMP folders) to retrieve all .java files and put them in the list
			String dir = directory.getName();
			String noExtensionPath = dir.substring(0, dir.length() - 4); // cut off the .jar from end of dir path  
			String jarDirectoryPath = noExtensionPath + "TEMP"; // add TEMP to end of path to get path to created TEMP folder 
			File jarDirectory = new File(jarDirectoryPath); // make file out of abstract path name to jarDirectory 
			searchFiles(jarDirectory, javaFiles);
		}
		else { // if path is invalid...
			System.out.println("Please specify a pathname to an existing directory/jar file.");
			return; 
		}
		
		Map<String, Integer[]> globalMap = new HashMap<String, Integer[]>(); // init the map of all (TypeName, {References, Declarations}) pairs. 
		
		// THIRD: parse all java files:
		int i = 0;
		while (i < javaFiles.size()) {
			File javaFile = javaFiles.get(i); // get next java file in list
			try {
				String source = readFile(javaFile); // concatenate all lines of file into a source string
				Map<String, Integer[]> localMap = visit(parse(source, javaFile.getParent())); // 1. parse the source string into an AST. 
																						   // 2. visit every node of the AST to find References/Declarations of any type
																						   // 3. store the returned map of number of refs/decs of each type found in file
				for (String key : localMap.keySet()) { // loop through every type in local map
					Integer[] globalCount = globalMap.get(key); // retrieve total number of refs/decs found for this type
					Integer[] localCount = localMap.get(key); // retrieve number of refs/decs found in this last file only
					if(globalCount != null) {// if key exists globally (type has been found in previous files)
						globalCount[0] += localCount[0]; // update the total count of references to this type
						globalCount[1] += localCount[1]; // update the total count of declarations to this type
					}	
					else { // if this type has been found for the first time in this file...
						globalCount = new Integer[] {localCount[0], localCount[1]}; // init the total count of refs/decs to this type
					}
					globalMap.put(key, globalCount); // update the global map
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		i++;
		}
			
		// Print info for EVERY type found:
		for (String key : globalMap.keySet()) {
			System.out.println("-------------------------------------------------------------------------------------------------");	
			System.out.format("%-50sDeclarations Found:%5d References Found:%5d\n", key, globalMap.get(key)[1], globalMap.get(key)[0]); 
		}
		
		// Finally: delete all TEMP folders to prevent clutter
		JarHandler.deleteTempFolders();
	}
	
	/**
	 * Constructor, sets which directory or jar file will be examined </br>	 * 
	 */
	public Main(String dir) 
	{
		this(new File(dir)); // make file out of abstract path name to directory/jar
	}
	
	/**
	 * Searches for all java files in a given directory and its sub-directories recursively
	 */
	public void searchFiles(File directory, List<File> javaFiles) {
		if (directory == null)
			return;
		if (directory.isDirectory()) {
			for (File file : directory.listFiles()) {
				searchFiles(file, javaFiles);
			}
		}
		else if (directory.isFile() && directory.getName().endsWith(".java")) {
			javaFiles.add(directory);
		}
	}

	/**
	 * Parses source String into AST
	 * @param sourceCode
	 *  Source String to be parsed by ASTParser
	 * @param sourcePath
	 *  Source path of the file to parse
	 * @return
	 * 	Root node of a parsed AST
	 */
	public ASTNode parse(String sourceCode, String sourcePath) {
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceCode.toCharArray());
		
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		parser.setUnitName("");
		
		parser.setEnvironment(null,
				new String[] {sourcePath}, new String[]{"UTF-8"}, true);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
		parser.setCompilerOptions(options);
		
		return parser.createAST(null);
	}
	
	/**
	 * Visits AST, counting references & declarations
	 * @param node
	 * Root node of a AST
	 * @return
	 * Map of (TypeName, {References, Declarations}) pairs found in AST
	 */
	public Map<String, Integer[]> visit(ASTNode node) {
		Visitor vis = new Visitor();
		CompilationUnit cu = (CompilationUnit)node;
		cu.accept(vis);
		Map<String, Integer[]> map = vis.getMap();
		return map; 
	}
	
	public static String readFile(File file) throws FileNotFoundException
	{
	    Scanner scanner = new Scanner(file);
	    String source = "";
	    while (scanner.hasNextLine()) {
	    	source += scanner.nextLine() + "\n";
	    } 
	    scanner.close();
	    return source;
	}
	
	private static File downloadFile(String urlString, File target) throws UnsupportedEncodingException, IOException
	{
		if (!target.exists()) {
			URL url = new URL(urlString);
			InputStream inputStream = url.openStream();
			Files.copy(inputStream, target.toPath(), StandardCopyOption.REPLACE_EXISTING);
		}
		
		return target;
	}
	
	public static File unzip(File zipFile, File outputDirectory) throws IOException 
	{      
		if (!zipFile.getName().endsWith(".zip")) { return null; }
		
		if (outputDirectory.exists()) {
			outputDirectory.delete();
		}
		outputDirectory.mkdirs();
		
	    final ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
	    final byte[] buffer = new byte[1024];

	    while (true) {
	    		ZipEntry zipEntry = zipInputStream.getNextEntry();
	    		if (zipEntry == null) { break; }
	    
	    		File zipEntryFile = new File(outputDirectory.getAbsolutePath() + File.separator + zipEntry.getName());
	    		if (zipEntry.isDirectory()) {
	    			zipEntryFile.mkdir();
	    		} else {
	    			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zipEntryFile));
		    		while (true) {
		    			int readBytes = zipInputStream.read(buffer, 0, buffer.length);
		    			if (readBytes <= 0) { break; }
		    			bufferedOutputStream.write(buffer, 0, readBytes);
		    		}
		    		bufferedOutputStream.close();
	    		}

	    		zipInputStream.closeEntry();
//	    		if (zipEntry.getName().contains(".java")) {  
//	    			StringBuilder stringBuilder = new StringBuilder();
//	    			while (inputStreamReader.read(buffer, 0, buffer.length) > 0) {
//	    				stringBuilder.append(new String(buffer));
//	    			}
//		    	
//	    			System.out.println("\t" + zipEntry.getName() + " read bytes " + stringBuilder.length()); 
//	    			// TODO: run type declaration counter code here!!!
//	    		}
	    }

	    zipInputStream.close();
	    
	    return outputDirectory;
	}
	
	public static void main(String[] args)
	{
		if (args.length == 1) {
			new Main(args[0]); // treat the argument as a path
		}
		else {
			try {
				String string = readFile(new File("assets/gitURLs.txt"));
				String[] gitURLs = string.split("\n");
				
				for (int i = 0; i < gitURLs.length; i += 1) {
					try {
						String gitURL = gitURLs[i];
						// Download the zip to simplify things
						String gitZipURL = gitURL + "/archive/master.zip";
						// Valid file/directory name for unzipping
						String fileName = gitURL.replaceAll("https://github.com/", "").replaceAll("/", "_");
						System.out.println(fileName);
						System.out.println("\tDownloading...");
						File zipFile = downloadFile(gitZipURL, new File("output/" + fileName + ".zip"));
						File outputDirectory = new File("output/" + fileName);
						System.out.println("\tExtracting...");
						outputDirectory = unzip(zipFile, outputDirectory);
						
						if (outputDirectory != null) {
							System.out.println("\tComplete");
							new Main(outputDirectory);
						} else {
							System.out.println("\tComplete");
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
//			System.out.println("Usage: java Main <directoryPath or jarPath>"); // error message to direct user how to properly run program
		}
	}
}
