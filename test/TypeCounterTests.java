package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import junit.framework.Assert;
import typeCounter.TypeCounter;


/**
 * Tests the type counter for functionality
 * @author Riike
 *
 */
@SuppressWarnings("deprecation")
public class TypeCounterTests {

	 final String BASEDIR = new File("src\\typeCounter").getAbsolutePath();
	
	 /**
		 * test that nothing is found when an invalid path is given
		 * 
		 */
	@Test
	public void IncorrectPath() {
		String dir = BASEDIR+"fake";
		TypeCounter tc = new TypeCounter(dir, "int");
		tc.run();
		
		Assert.assertEquals(0, tc.getDeclarationCount());
		Assert.assertEquals(0, tc.getReferenceCount());
		//System.out.println(tc.getPath());
	}
	/**
	 * test that the Declaration counter is never incremented, remains at 0, when an invalid type is given
	 * 
	 */
	@Test
	public void NonExistingType() {  
		TypeCounter tc = new TypeCounter(BASEDIR, "ENUM");
		tc.run();
		Assert.assertEquals(0, tc.getDeclarationCount());
	}
	/**
	 * test that the References counter is never increment, remains at 0, when an invalid type is given
	 * 
	 */
	@Test
	public void NonExistingType2() {  
		TypeCounter tc = new TypeCounter(BASEDIR, "ENUM");
		tc.run();
		Assert.assertEquals(0, tc.getReferenceCount());
	}
	/**
	 * test that when the parameters type AND path are not valid, or null
	 * that the constructor set the type is still empty
	 */
	@Test//(expected = NullPointerException.class)
	public void SettingFolderPathNoType() {
		TypeCounter tc = new TypeCounter(BASEDIR, null);
		assertNull(tc.getType());
	}
	/**
	 * testing the getPath method to see that the parameters path is equal to the 
	 * path inputed by the user
	 */
	@Test
	public void SettingFolderPathType() {
		TypeCounter tc = new TypeCounter(BASEDIR, "int");
		assertEquals(BASEDIR, tc.getPath());
	}
	/**
	 * testing that if the path entered is null, and when the typeCounter is run then a null
	 * pointer is thrown 
	 */
	@Test(expected = NullPointerException.class)
	public void SettingTypeNoPath() {
		TypeCounter tc = new TypeCounter(null, "int");
		tc.run();
	}
	/**
	 * testing that if the type entered, the getter for the getType is correct, 
	 * equals the correct value
	 */
	@Test
	public void SettingTypePath() {
		String type = "int";
		TypeCounter tc = new TypeCounter(BASEDIR, type);
		Assert.assertEquals(type, tc.getType());
	}
	
}
