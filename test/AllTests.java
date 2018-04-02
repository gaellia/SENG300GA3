package test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	MainClassTests.class,
	TypeCounterTests.class, 
	DummyDirTests.class,
	DummyJarTests.class
})
public class AllTests {
	
}
