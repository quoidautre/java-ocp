public class TestDuo {
    public static void main(String[] args) {
        Duo<String, Boolean> dual = new Duo<String, Boolean>("toto", true);
        System.out.println("Valeur de l'objet dual : val1 = " + dual.getValeur1() + ", val2 = " + dual.getValeur2());

        Duo<Double, Character> dual2 = new Duo<Double, Character>(12.2585, 'C');
        System.out.println("Valeur de l'objet dual2 : val1 = " + dual2.getValeur1() + ", val2 = " + dual2.getValeur2());
    }
}
