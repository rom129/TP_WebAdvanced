package ex01C; 
import org.apache.log4j.*;
 
public class Log4jBasics { 
	protected static Logger log=Logger.getLogger(Log4jBasics.class);

	/**
	 * This methd allows to understand how we can use logs.
	 * @param args
	 */
	public static void main(String[] args) { 
		log.debug("DEBUG: Cool !"); 		// n'aparait que lorsqu'il y a la config log4j.rootLogger=DEBUG, dans log4j.properties
		log.info("INFO: Cool !"); 
	} 
}