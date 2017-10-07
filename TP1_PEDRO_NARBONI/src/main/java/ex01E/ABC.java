package ex01E;

class A {
    int x;
    
    void m() {
        System.out.println("Je suis dans la méthode m d'une instance de A");
    }
}

class B extends A {
    int x;
    
    void m() {
        System.out.println("Je suis dans la méthode m d'une instance de B");
    }
}

class C extends B {

    int x, a;
    
    void m() {
        System.out.println("Je suis dans la méthode m d'une instance de C");
    }
    
    void test() {
        a = super.x;
        a = super.x; // Ligne corrigé
        a = ((B)this).x;
        a = ((A)this).x;
        super.m();
        super.m(); // Ligne corrigé
        ((B)this).m(); // (1)
    }
}

//Questions :
//-> ((B)this).m(); // (1) -->Correspond à l'execution de la methode m() de l'instance de l'object C
//-> Oui, on s'attandais à avoir ce resultat puisqu'on fait reference à l'object courrant avec "this"
//-> Il s'agit d'un polymorphisme de type ad hoc, puisqu'on utilise la surcharge de la methode m()