package main;

import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Test;


/**Warning the system test will run on urls in assets/gitURLs.txt, please modify files in there
 * if it runs on all files in there it'll take far too long
 * also some repositories that were too big were manually downloaded in advanced and used 
 *
 **/
public class Tests {
	//replace this with path name on your computer
	String BASEDIR = "C:\\Users\\Jocelyn\\Documents\\GitHub\\SENG300GA3\\Tests";
	//This collection of tests will be tests for the visitor class
	
	//Visitor class to be used by other method
	Visitor visitor =new Visitor();
	//This test ensures that normal types are counted 
	@Test
	public void testOtherTypes()
	{
		//create a parser
		int before=visitor.getOtherCount();
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);
		
		parser.setUnitName("");
		
		parser.setEnvironment(null,
				new String[] {""}, new String[]{"UTF-8"}, true);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
		parser.setCompilerOptions(options);
		String source="public class A { }";
		parser.setSource(source.toCharArray());
		CompilationUnit cu= (CompilationUnit) parser.createAST(null);
		cu.accept(visitor);
		before++;
		assertEquals(visitor.getOtherCount(), before);
		
	}
	
	//test it can find an anon type declaration 
	@Test
	public void testAnonTypes()
	{
		//create a parser
		int before=visitor.getAnonCount();
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);	
		parser.setUnitName("");
		parser.setEnvironment(null,
				new String[] {""}, new String[]{"UTF-8"}, true);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
		parser.setCompilerOptions(options);
		String source="public class A { secret shh=new secret() {}; }";
		parser.setSource(source.toCharArray());
		CompilationUnit cu= (CompilationUnit) parser.createAST(null);
		cu.accept(visitor);
		before++;
		assertEquals(visitor.getAnonCount(), before);
		
	}
	
	//Test a class in a class
	@Test
	public void testNestedTypes()
	{
		//create a parser
		int before=visitor.getNestCount();
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);	
		parser.setUnitName("");
		parser.setEnvironment(null,
				new String[] {""}, new String[]{"UTF-8"}, true);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
		parser.setCompilerOptions(options);
		String source="public class A { class nest {String=\"egg\";}; }";
		parser.setSource(source.toCharArray());
		CompilationUnit cu= (CompilationUnit) parser.createAST(null);
		cu.accept(visitor);
		before++;
		assertEquals(visitor.getNestCount(), before);
		
	}
	
	
	//test local classes
	@Test
	public void testLocalTypes()
	{
		//create a parser
		int before=visitor.getLocalCount();
		ASTParser parser = ASTParser.newParser(AST.JLS9);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
		parser.setBindingsRecovery(true);	
		parser.setUnitName("");
		parser.setEnvironment(null,
				new String[] {""}, new String[]{"UTF-8"}, true);
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5); //or newer version
		parser.setCompilerOptions(options);
		String source="public class A { void amethod() {class local {}; }}";
		parser.setSource(source.toCharArray());
		CompilationUnit cu= (CompilationUnit) parser.createAST(null);
		cu.accept(visitor);
		before++;
		assertEquals(visitor.getLocalCount(), before);
		
	}
	
	//These tests test the zip functionality of main
	@Test 
	public void testZip() throws UnsupportedEncodingException, IOException
	{
		AtomicInteger aInt=new AtomicInteger(0);
	try {
		String string = "https://github.com/gaellia/SENG300GA3"; //this is our git repository
		String[] gitURLs = {string};
		File outputDirectory = null;
		
		for (int i = 0; i < gitURLs.length; i += 1) {
			String gitURL = gitURLs[i];
			try {
				gitURL = gitURL.replaceFirst("(.git)$", "");
				// Download the zip to simplify things
				String gitZipURL = gitURL + "/archive/master.zip";
				// Valid file/directory name for unzipping
				String fileName = gitURL.replaceAll("https://github.com/", "").replaceAll("/", "_");
				File zipFile = Main.downloadFile(gitZipURL, new File("output/" + fileName + ".zip"));
				outputDirectory = new File("output/" + fileName);
				outputDirectory = Main.unzip(zipFile, outputDirectory);
				assertNotNull(outputDirectory);
			}finally{}
		}
	}finally{}
	}
	
	//test fake url
	@Test
	public void fakeurltest() throws UnsupportedEncodingException, IOException {
		
		String fakeurl="http://www.checkshorturl.com/";//needs to be semi real to work
		File notReal=new File("nhkfgjh");
		notReal=Main.downloadFile(fakeurl, notReal);
		assertNotNull(notReal);
		notReal.delete();
	}
	
	//tests for jarHandler.java
	// Testing a Jar File
		@Test
		public void JarTest() {
		    String path = BASEDIR + "\\jarTest";   // subDirectory: directory with specific file to test
		    AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);

		    
		    File dir = new File(path);
		    
		    List<File> javaFiles = new ArrayList<File>();
		    
			try {
				JarHandler.extractJars(dir);
			} catch (IOException e1) {
				e1.printStackTrace();
			}	
			
		    main.searchFiles(dir, javaFiles);
		    String source;
			try {
			    File f = javaFiles.get(0);
			    source = main.readFile(f);
			    	
			    Map<String, Integer[]> localMap = main.visit(main.parse(source,  f.getParent()));
		
			    int refCount = localMap.get("Foo.Bar")[0];
			    int decCount = localMap.get("Foo.Bar")[1];
			    
			    JarHandler.deleteTempFolders();
		
			    assertEquals(refCount, 2);
			    assertEquals(decCount, 1);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}

		//These were the old tests for functionality of the vistor class 
		// test that an import statement counts as 1 reference
		@Test
		public void ImportTest1() {

			String path = BASEDIR + "\\T1"; 
			AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);
			
			File dir = new File(path); 
			
			List<File> javaFiles = new ArrayList<File>(); 
			
			main.searchFiles(dir, javaFiles);
			String source;
			try {
				File f = javaFiles.get(0); 
				source = main.readFile(f);
				Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
				int refCount = localMap.get("java.util.HashMap")[0]; 
				assertEquals(refCount, 1); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			 
		}
		
		// test that a 2d array of local type counts as 1 reference to the 2d array 
		@Test
		public void LocalClassArrayTest1() {

			String path = BASEDIR + "\\T3"; 
			AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);
			
			File dir = new File(path); 
			
			List<File> javaFiles = new ArrayList<File>(); 
			
			main.searchFiles(dir, javaFiles);
			String source;
			try {
				File f = javaFiles.get(0); 
				source = main.readFile(f);
				Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
				int refCount = localMap.get("Local[][]")[0]; 
				assertEquals(refCount, 1); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			 
		}
		
		// test that a parameterized type counts as a reference to the generic type
		@Test
		public void ParameterizedTypeTest1() {

			String path = BASEDIR + "\\T4"; 
			AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);
			
			File dir = new File(path); 
			
			List<File> javaFiles = new ArrayList<File>(); 
			
			main.searchFiles(dir, javaFiles);
			String source;
			try {
				File f = javaFiles.get(0); 
				source = main.readFile(f);
				Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
				int refCount = localMap.get("java.util.Map")[0]; 
				assertEquals(refCount, 2); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			 
		}
		
		
		// test that a constructor declaration counts as a reference to the class itself
		@Test
		public void ConstructorDeclarationTest1() {

			String path = BASEDIR + "\\T5"; 
			AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);
			
			File dir = new File(path); 
			
			List<File> javaFiles = new ArrayList<File>(); 
			
			main.searchFiles(dir, javaFiles);
			String source;
			try {
				File f = javaFiles.get(0); 
				source = main.readFile(f);
				Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
				int refCount = localMap.get("T5.ConstructorDeclarationTest1")[0]; 
				assertEquals(refCount, 1); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			 
		}
		
		// Enum nested inside Interface
		@Test
		public void EnumTest() {

			String path = BASEDIR + "\\T7"; 
			AtomicInteger aInt=new AtomicInteger(0);
		    Main main=new Main(BASEDIR, false, aInt,aInt,aInt, aInt);
			
			File dir = new File(path); 
			
			List<File> javaFiles = new ArrayList<File>(); 
			
			main.searchFiles(dir, javaFiles);
			String source;
			try {
				File f = javaFiles.get(0); 
				source = main.readFile(f);
				Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
				int decCount = localMap.get("T7.OuterInterface.InnerEnum")[1]; 
				assertEquals(decCount, 1); 
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			 
		}
		
		//system test
		@Test
		public void systemTestwitharg() throws IOException {
			String[] str= {BASEDIR};
			Main.main(str);
			File csvFile = new File("output/" + "results.csv");
			String notnull=Main.readFile(csvFile);
			assertNotNull(notnull);
		}
		
		@Test
		public void systemTestNoarg() throws FileNotFoundException {
			String[] str= {};
			Main.main(str);
			File csvFile = new File("output/" + "results.csv");
			String notnull=Main.readFile(csvFile);
			assertNotNull(notnull);
		}
}

