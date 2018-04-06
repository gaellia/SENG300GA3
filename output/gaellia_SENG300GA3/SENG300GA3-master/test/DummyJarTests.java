package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import typeCounter.TypeCounter;

/**
 * Tests, with a dummy jar file, for functionality
 * @author Riike
 *
 */
@SuppressWarnings("deprecation")
public class DummyJarTests {

	//final String BASEJAR = new File("jar_files").getAbsolutePath();
	final String BASEJAR = "/Users/Riike/Desktop/SENG300_A2.jar";

	@Test
	void test() {
		TypeCounter tc = new TypeCounter(BASEJAR, "A");
		tc.run();
		//Assert.assertNotSame(0,tc.getDeclarationCount());
	}

}
