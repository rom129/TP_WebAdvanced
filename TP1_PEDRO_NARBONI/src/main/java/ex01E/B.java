package ex01E;

class B extends A {
    int x=5;
    
    /**
     * This method is used to overload the method m() of the A class.
     */
    void m() {
        System.out.println("Je suis dans la méthode m d'une instance de B");
    }
}
