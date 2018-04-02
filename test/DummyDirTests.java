package test;

import java.io.File;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import typeCounter.TypeCounter;


/**
 * Test, with the test cases provided, for functionality
 * @author Riike
 *
 */
@SuppressWarnings("deprecation")
public class DummyDirTests {

	final String BASEDIR = new File("src").getAbsolutePath();
	//final String BASEJAR = new File("jar_files").getAbsolutePath();
	//final String BASEDIR = "/Users/Riike/Desktop/SENG300_A2/src";
	/**
	 * Tests for correct output for declarations of a class
	 */
	@Test
	void TestDirClass() {
		TypeCounter tc = new TypeCounter(BASEDIR, "A");
		tc.run();
		Assert.assertNotSame(0,tc.getDeclarationCount());
	}
	
	/**
	 * Test for correct output for class reference count
	 */
	@Test
	void TestDirClass2() {
		TypeCounter tc = new TypeCounter(BASEDIR, "E");
		tc.run();
		Assert.assertNotSame(0,tc.getReferenceCount());
	}
	
	/**
	 * Tests for correct output for primitive type int 
	 */
	@Test
	void TestDirPrimitive1() {
		TypeCounter tc = new TypeCounter(BASEDIR, "int");
		tc.run();
		Assert.assertEquals(0, tc.getDeclarationCount());
		Assert.assertNotSame(0,tc.getReferenceCount());
	}
	/**
	 * Tests for correct output for primitive type boolean 
	 */
	@Test
	void TestDirPrimitive2() {
		TypeCounter tc = new TypeCounter(BASEDIR, "boolean");
		tc.run();
		Assert.assertEquals(0,tc.getDeclarationCount());
		//Assert.assertSame(1,tc.getReferenceCount());
	}
	/**
	 * Tests for correct output for primitive type short 
	 */
	@Test
	void TestDirPrimitive3() {
		TypeCounter tc = new TypeCounter(BASEDIR, "short");
		tc.run();
		Assert.assertEquals(0,tc.getDeclarationCount());
		//Assert.assertSame(1,tc.getReferenceCount());
	}
	/**
	 * Tests for correct output for primitive type byte 
	 */
	@Test
	void TestDirPrimitive4() {
		TypeCounter tc = new TypeCounter(BASEDIR, "byte");
		tc.run();
		Assert.assertEquals(0,tc.getDeclarationCount());
		//Assert.assertSame(1,tc.getReferenceCount());
	}
	
	/**
	 * Tests for correct output for declaration count for class with other classes within it 
	 */
	@Test
	void TestDirSuperClass() {
		TypeCounter tc = new TypeCounter(BASEDIR, "C");
		tc.run();
		Assert.assertNotSame(0,tc.getDeclarationCount());
	}
	/**
	 * Test for correct output for interface declaration
	 */
	@Test
	void TestDirClassInterface() {
		TypeCounter tc = new TypeCounter(BASEDIR, "B");
		tc.run();
		Assert.assertNotSame(0,tc.getDeclarationCount());
	}
	/**
	 * Test for correct output for class within a package
	 */
	@Test
	void TestDirClassInPackage() {
		TypeCounter tc = new TypeCounter(BASEDIR, "foo.E");
		tc.run();
		Assert.assertEquals(0,tc.getDeclarationCount());
	}
	/**
	 * Test for correct output for package reference count
	 */
	@Test
	void TestDirPackage() {
		TypeCounter tc = new TypeCounter(BASEDIR, "test");
		tc.run();
		Assert.assertNotSame(0,tc.getReferenceCount());
	}
}
