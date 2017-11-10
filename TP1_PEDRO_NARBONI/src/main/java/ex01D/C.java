package ex01D;

import org.apache.log4j.Logger;

public class C {
	
	protected static Logger log=Logger.getLogger(C.class);
	
	/**
	 * This method shows the difference between an object reference and a primitive type.
	 * @param i
	 * @param s
	 */
	//  void methode1(int i, StringBuffer s) {
	static void methode1(int i, StringBuffer s) { // void method devient static void methode1
		i++; 
		s.append("d");
		log.info("value of i is : " + i + "and message is : " + s);
	}
	
	/**
	 * Main method.
	 * @param args
	 */
	public static void main(String [] args) {
		int i = 0;
		StringBuffer s = new StringBuffer("abc");
		methode1(i, s);
		//System.out.println(i=" + i + ", s=" + s);
		System.out.println("i=" + i + ", s=" + s); // i=0, s=abcd
		log.info("value of i is : " + i + "and message is : " + s);
	}
}