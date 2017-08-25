
public class TestVille {
    public static void main(String[] args)
    {
        Ville v = null;

        try {
            v = new Ville("Rddde", 12000, "France");
        }

        //Gestion de l'exception sur le nombre d'habitants
        catch (NombreHabitantException e) {
            e.printStackTrace();
        }

        //Gestion de l'exception sur le nom de la ville
        catch(NomVilleException e2){
            System.out.println(e2.getMessage());
        }
        finally{
            if(v == null)
                v = new Ville();
        }

        System.out.println(v.toString());
    }
}