package test;

import java.io.File;

import org.junit.Test;

import typeCounter.Main;

/**
 * Tests the main class for argument handling
 * @author Riike
 *
 */
public class MainClassTests {
	 
	final String BASEDIR = new File("src\typeCounter").getAbsolutePath();
	/**
	 * test the main class when no arguments/null arguments are given
	 * 
	 */
	@Test(expected = NullPointerException.class)
	public void TestArgumentsDNE() {
		String[] args = {null};
		Main.main(args);
	}
	/**
	 * test the main class when an incorrect path name is given
	 * (should not affect the main class)
	 * 
	 */
	@Test
	public void TestArgumentsIncorretcPath() {
		String[] args = {BASEDIR+"fake"};
		Main.main(args);
	}
	/**
	 * test the main class when a path is given, which is valid
	 * 
	 */
	@Test
	public void TestArgumentsCorrectPath() {
		String[] args = {BASEDIR};
		Main.main(args);
	}

}
