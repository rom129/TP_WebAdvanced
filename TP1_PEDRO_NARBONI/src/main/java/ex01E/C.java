package ex01E;

class C extends B {

    int x, a;
    
    void m() {
        System.out.println("Je suis dans la méthode m d'une instance de C");
    }
    
    void test() {
        a = super.x;
        // a = super.super.x; 
		// Il est impossible de faire super.super car ça ne respecte pas les règles d'encapsulation.
        a = ((B)this).x;
        a = ((A)this).x;
        super.m();
        // super.super.m(); 
		// Il est impossible de faire super.super car ça ne respecte pas les règles d'encapsulation.
        ((B)this).m(); // (1)
    }
}

//Questions :
//-> ((B)this).m(); // (1) -->Correspond à l'execution de la methode m() de l'instance de l'object C

//-> En regardant le code, on ne s'attend pas à ce résultat car le cast nous pousse à penser que la méthode m() appelé est celle de la classe B.
// On voit que sur le this, le Cast avec une autre classe ne permet pas de récupérer ses méthodes mais seulement ses attributs.

//-> Il s'agit du multilevel inheritance : héritage multiple