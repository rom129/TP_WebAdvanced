package ex01F; 

public class ToStringOverloading { 

	public static void main(String[] args) { 
		Circle c1 = new Circle(0,0, 5); 
		Circle c2 = new Circle(0,0, 3); 
		System.out.println("C1 => " + c1); 
	} 
}

//Questions :
//--> Il faut savoir que chaque classe � une m�thode toString() par d�faut.
// Lorsque l'on instancie cette classe un message contenant le nom de la classe et l'adresse en hexa est automatiquement g�n�r� par cette m�thode.
// Lorsque l'on fait un Syso sur cette instance,  on voit apparaitre ce message. 
// Comme ici, on red�finit et surcharge la m�thode avec un autre message, celui ci est appel� sans que la m�thode soit nomm�.

//--> Lorsque l'on renomme la m�thode, celle ci ne surcharge plus la m�thode par d�faut.
// Le message affich� redevient donc le nom de la classe et son adresse en hexa : ex01F.Circle@32cfa69a