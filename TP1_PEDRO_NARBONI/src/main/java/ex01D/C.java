package ex01D;
public class C {
	
	//  void methode1(int i, StringBuffer s) {
	static void methode1(int i, StringBuffer s) { // void method devient static void methode1
		i++; 
		s.append("d");
	}
	
	public static void main(String [] args) {
		int i = 0;
		StringBuffer s = new StringBuffer("abc");
		methode1(i, s);
		//System.out.println(i=" + i + ", s=" + s);
		System.out.println("i=" + i + ", s=" + s); // i=0, s=abcd
	}
}