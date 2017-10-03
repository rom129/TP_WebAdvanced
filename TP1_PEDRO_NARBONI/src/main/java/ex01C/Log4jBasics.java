package ex01C; 
import org.apache.log4j.*;
 
public class Log4jBasics { 
	protected static Logger log = Logger.getLogger(Log4jBasics.class);

	public static void main(String[] args) { 
		log.debug("DEBUG: Cool !");
		log.info("INFO: Cool !"); 
	} 
}