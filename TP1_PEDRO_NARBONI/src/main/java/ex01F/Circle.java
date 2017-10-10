package ex01F;

import org.apache.log4j.Logger; 

public class Circle { 
	protected static Logger log = Logger.getLogger(Circle.class); 
	int x; 
	int y; 
	int radius;
	
	/**
	 * Constructor.
	 * @param x
	 * @param y
	 * @param radius
	 */
	public Circle(int x, int y, int radius) { 
		this.x = x; 
		log.debug("this.x = " + x);
		this.y = y; 
		log.debug("this.y = " + y);
		this.radius = radius; 
		log.debug("this.radius = " + radius);
	} 
	
	/**
	 * This method overload the default toString() method of the Class.
	 * @return
	 */
	public String toString() { 
		return( String.format("Circle with center (%d,%d) and radius %d (Perimter is %,.2f)", 
			this.x, 
			this.y, 
			this.radius, 
			(2 * java.lang.Math.PI * this.radius)) 
		); 
	} 
}