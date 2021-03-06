import java.io.BufferedInputStream;
import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileNio {
    public static void main(String[] args) {
        FileInputStream fis;
        BufferedInputStream bis;
        FileChannel fc;

        try {
            //Création des objets
            fis = new FileInputStream(new File("dictionnaire.txt"));
            bis = new BufferedInputStream(fis);
            //Démarrage du chrono
            long time = System.currentTimeMillis();
            //Lecture
            while(bis.read() != -1);
            //Temps d'exécution
            System.out.println("Temps d'exécution avec un buffer conventionnel : " + (System.currentTimeMillis() - time));

            //Création d'un nouveau flux de fichier
            fis = new FileInputStream(new File("dictionnaire.txt"));
            //On récupère le canal
            fc = fis.getChannel();
            //On en déduit la taille
            int size = (int)fc.size();
            //On crée un buffer correspondant à la taille du fichier
            ByteBuffer bBuff = ByteBuffer.allocate(size);

            //Démarrage du chrono
            time = System.currentTimeMillis();
            //Démarrage de la lecture
            fc.read(bBuff);
            //On prépare à la lecture avec l'appel à flip
            bBuff.flip();
            //Affichage du temps d'exécution
            System.out.println("Temps d'exécution avec un nouveau buffer : " + (System.currentTimeMillis() - time));

            //Puisque nous avons utilisé un buffer de byte afin de récupérer les données
            //Nous pouvons utiliser un tableau de byte
            //La méthode array retourne un tableau de byte
            byte[] tabByte = bBuff.array();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
/*
            if(fis != null) fis.close();
            if(bis != null) bis.close();
            if(fc != null) fc.close();
*/
        }
    }
}

/*
try(FileInputStream fis = new FileInputStream("test.txt");
FileOutputStream fos = new FileOutputStream("test2.txt")) {
  byte[] buf = new byte[8];
  int n = 0;
  while((n = fis.read(buf)) >= 0){
    fos.write(buf);
    for(byte bit : buf)
      System.out.print("\t" + bit + "(" + (char)bit + ")");

    System.out.println("");
  }

  System.out.println("Copie terminée !");

} catch (IOException e) {
  e.printStackTrace();
}
 */

/*
try (FileInputStream fis = new FileInputStream("test.txt");
  ObjectInputStream ois = new ObjectInputStream(fis)) {
    //…
}
 */