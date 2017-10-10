package ex01F;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Unit test for simple App.
 */
public class CircleTest extends TestCase{
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public CircleTest(String testName) {
		super(testName);
	}
	
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		System.out.println("COUCOU");
		return new TestSuite( CircleTest.class );
	}
	
	/**
	 * This method allows to test the fact that the new method toString() 
	 * overload the default method toString() 
	 */
	public void testCircle() {
		Circle c1 = new Circle(0,0, 5);
		String s = c1.toString();
		assertEquals(s,c1+"");
	}
}