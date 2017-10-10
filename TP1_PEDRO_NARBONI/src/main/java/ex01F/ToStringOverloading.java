package ex01F; 

import org.apache.log4j.Logger;

public class ToStringOverloading { 
	
	protected static Logger log=Logger.getLogger(ToStringOverloading.class);
	
	/**
	 * Main method. 
	 * @param args
	 */
	public static void main(String[] args) { 
		Circle c1 = new Circle(0,0, 5); 
		Circle c2 = new Circle(0,0, 3); 
		System.out.println("C1 => " + c1);
		log.info("C1 => " + c1);
		log.info("C1.toString() => " + c1.toString());
		
	} 
}

//Questions :
//--> Il faut savoir que chaque classe  une méthode toString() par défaut.
// Lorsque l'on instancie cette classe un message contenant le nom de la classe et l'adresse en hexa est automatiquement généré par cette méthode.
// Lorsque l'on fait un Syso sur cette instance,  on voit apparaitre ce message. 
// Comme ici, on redéfinit et surcharge la méthode avec un autre message, celui ci est appelé  sans que la méthode soit nommé.

//--> Lorsque l'on renomme la méthode, celle ci ne surcharge plus la m�thode par d�faut.
// Le message affiché redevient donc le nom de la classe et son adresse en hexa : ex01F.Circle@32cfa69a