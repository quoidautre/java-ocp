
public class TestSolo {
    public static void main(String[] args) {
        Solo<Integer> val = new Solo<Integer>();
        System.out.println(val.getValeur());

        Solo<String> valS = new Solo<String>("TOTOTOTO");
        System.out.println(valS.getValeur());

        Solo<Float> valF = new Solo<Float>(12.2f);
        System.out.println(valF.getValeur());

        Solo<Double> valD = new Solo<Double>(12.202568);
        System.out.println(valD.getValeur());
    }
}
