public class TestGateau_1 {

    public static void main(String[] args){
        Patisserie pat = new CoucheChocolat(
                              new CoucheCaramel(
                                    new CoucheBiscuit(
                                        new CoucheChocolat(
                                            new Gateau()
                                        )
                                    )
                              )
        );
        System.out.println(pat.preparer());
    }
}