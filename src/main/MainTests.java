package main;

import static org.junit.Assert.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class MainTests {
	
	String BASEDIR = "some-path"; // Change this to the directory of the Tests folder
	
	
	// test that an import statement counts as 1 reference
	@Test
	public void ImportTest1() {

		String path = BASEDIR + "\\T1"; 
		Main main = new Main(path); // only for instantiation
		
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
	
	// test that a local class declaration only has a simple name
	@Test
	public void LocalClassTest1() {

		String path = BASEDIR + "\\T2"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("Local")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// test that a 2d array of local type counts as 1 reference to the 2d array 
	@Test
	public void LocalClassArrayTest1() {

		String path = BASEDIR + "\\T3"; 
		Main main = new Main(path); // only for instantiation
		
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
	
	// test that a 2d array of local type counts as 1 reference to the 1d array 
	@Test
	public void LocalClassArrayTest2() {

		String path = BASEDIR + "\\T3"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int refCount = localMap.get("Local[]")[0]; 
			assertEquals(refCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}
	
	// test that a 2d array of local type counts as 1 reference to the local type
	@Test
	public void LocalClassArrayTest3() {

		String path = BASEDIR + "\\T3"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int refCount = localMap.get("Local")[0]; 
			assertEquals(refCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}
	
	// test that a parameterized type counts as a reference to the generic type
	@Test
	public void ParameterizedTypeTest1() {

		String path = BASEDIR + "\\T4"; 
		Main main = new Main(path); // only for instantiation
		
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
	
	// test that a parameterized type counts as a reference to first parameter type
	@Test
	public void ParameterizedTypeTest2() {

		String path = BASEDIR + "\\T4"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int refCount = localMap.get("java.lang.String")[0]; 
			assertEquals(refCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// test that a parameterized type counts as a reference to second parameter type (array)
	@Test
	public void ParameterizedTypeTest3() {

		String path = BASEDIR + "\\T4"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
	
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int refCount = localMap.get("int")[0]; 
			assertEquals(refCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// test that a parameterized type counts as a reference to second parameter type (primitive)
	@Test
	public void ParameterizedTypeTest4() {

		String path = BASEDIR + "\\T4"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int refCount = localMap.get("int[]")[0]; 
			assertEquals(refCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// test that a constructor declaration counts as a reference to the class itself
	@Test
	public void ConstructorDeclarationTest1() {

		String path = BASEDIR + "\\T5"; 
		Main main = new Main(path); // only for instantiation
		
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
	
	// Class nested inside Class
	@Test
	public void T6a() {

		String path = BASEDIR + "\\T6"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T6.OuterClass.InnerClass")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}
	
	// Interface nested inside Class
	@Test
	public void T6b() {

		String path = BASEDIR + "\\T6"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T6.OuterClass.InnerInterface")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}
	
	// Enum nested inside Class
	@Test
	public void T6c() {

		String path = BASEDIR + "\\T6"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T6.OuterClass.InnerEnum")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Annote nested inside Class
	@Test
	public void T6d() {

		String path = BASEDIR + "\\T6"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T6.OuterClass.InnerAnnote")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Class nested inside Interface
	@Test
	public void T7a() {

		String path = BASEDIR + "\\T7"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T7.OuterInterface.InnerClass")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}		
	
	// Interface nested inside Interface
	@Test
	public void T7b() {

		String path = BASEDIR + "\\T7"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T7.OuterInterface.InnerInterface")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Enum nested inside Interface
	@Test
	public void T7c() {

		String path = BASEDIR + "\\T7"; 
		Main main = new Main(path); // only for instantiation
		
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
	
	// Annote nested inside Interface
	@Test
	public void T7d() {

		String path = BASEDIR + "\\T7"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T7.OuterInterface.InnerAnnote")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}
	
	// Class nested inside Enum
	@Test
	public void T8a() {

		String path = BASEDIR + "\\T8"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T8.OuterEnum.InnerClass")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Interface nested inside Enum
	@Test
	public void T8b() {

		String path = BASEDIR + "\\T8"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T8.OuterEnum.InnerInterface")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Enum nested inside Enum
	@Test
	public void T8c() {

		String path = BASEDIR + "\\T8"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T8.OuterEnum.InnerEnum")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Annote nested inside Enum
	@Test
	public void T8d() {

		String path = BASEDIR + "\\T8"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T8.OuterEnum.InnerAnnote")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Class nested inside Annote
	@Test
	public void T9a() {

		String path = BASEDIR + "\\T9"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T9.OuterAnnote.InnerClass")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Interface nested inside Annote
	@Test
	public void T9b() {

		String path = BASEDIR + "\\T9"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T9.OuterAnnote.InnerInterface")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Enum nested inside Annote
	@Test
	public void T9c() {

		String path = BASEDIR + "\\T9"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T9.OuterAnnote.InnerEnum")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Annote nested inside Annote
	@Test
	public void T9d() {

		String path = BASEDIR + "\\T9"; 
		Main main = new Main(path); // only for instantiation
		
		File dir = new File(path); 
		
		List<File> javaFiles = new ArrayList<File>(); 
		
		main.searchFiles(dir, javaFiles);
		String source;
		try {
			File f = javaFiles.get(0); 
			source = main.readFile(f);
			Map<String, Integer[]> localMap = main.visit(main.parse(source, f.getParent()));
			int decCount = localMap.get("T9.OuterAnnote.InnerAnnote")[1]; 
			assertEquals(decCount, 1); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		 
	}	
	
	// Anonymous Class Test
	@Test
	public void AnonClassTest() {
	    String path = BASEDIR + "\\AnonClassTest";   // subDirectory: directory with specific file to test
	    Main main = new Main(path);
	    
	    File dir = new File(path);
	    
	    List<File> javaFiles = new ArrayList<File>();
		
	    main.searchFiles(dir, javaFiles);
	    String source;
	    try {
	    	File f = javaFiles.get(0);
	    	source = main.readFile(f);
	    	
	    	Map<String, Integer[]> localMap = main.visit(main.parse(source,  f.getParent()));
	    	int	refCountA = localMap.get("A")[0];
	    	int	decCountA = localMap.get("A")[1];
	    	
	    	int refCountAnon = localMap.get("AnonClass")[0];
	    	int decCountAnon = localMap.get("AnonClass")[1];
	    	
	    	int refCountKey = localMap.get("LA$47; (Anonymous Class)")[0]; // Key may change for different devices
	    	int decCountKey = localMap.get("LA$47; (Anonymous Class)")[1];
	    	    	
	    	
	    	assertEquals(refCountA, 1);
	    	assertEquals(decCountA, 1);
	    	assertEquals(refCountAnon, 1);
	    	assertEquals(decCountAnon, 0);
	    	assertEquals(refCountKey, 0);
	    	assertEquals(decCountKey, 1);
	    }
	    catch (FileNotFoundException e) {
	    	e.printStackTrace();
	    }
	}
	
	// Testing a Jar File
	@Test
	public void JarTest() {
	    String path = BASEDIR + "\\jarTest";   // subDirectory: directory with specific file to test
	    Main main = new Main(path);
	    
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
}

