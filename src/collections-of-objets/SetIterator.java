import java.util.HashSet;
import java.util.Iterator;

public class SetIterator {
    public static void main(String[] args) {
        HashSet hs = new HashSet();
        hs.add("toto");
        hs.add(12);
        hs.add('d');

        Iterator it = hs.iterator();
        while(it.hasNext())
            System.out.println(it.next());

        System.out.println("\nParcours avec un tableau d'objet");
        System.out.println("-----------------------------------");

        Object[] obj = hs.toArray();
        for(Object o : obj)
            System.out.println(o);
    }
}