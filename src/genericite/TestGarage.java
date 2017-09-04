import java.util.ArrayList;
import java.util.List;

public class TestGarage {
    public static void main(String[] args) {
        List<Voiture> listVoiture = new ArrayList<Voiture>();
        listVoiture.add(new Voiture());

        List<VoitureSansPermis> listVoitureSP = new ArrayList<VoitureSansPermis>();
        listVoitureSP.add(new VoitureSansPermis());

        Garage garage = new Garage();
        garage.add(listVoiture);
        System.out.println("--------------------------");
        garage.add(listVoitureSP);
    }
}