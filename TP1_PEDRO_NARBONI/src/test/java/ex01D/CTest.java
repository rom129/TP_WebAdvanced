package ex01D;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
/**
 * Unit test for simple App.
 */
public class CTest extends TestCase{
	
	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public CTest(String testName) {
		super(testName);
	}
	
	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		System.out.println("COUCOU");
		return new TestSuite( CTest.class );
	}
	
	/**
	 * method to test the method methode1().
	 */
	public void testC() {
		int i = 0;
		C c = new C();
		StringBuffer s = new StringBuffer("abc");
		assertEquals(s.toString(),"abc");
		assertEquals(i,0);
		c.methode1(i, s);
		assertEquals(s.toString(),"abcd");
		assertEquals(i,0);
	}
}