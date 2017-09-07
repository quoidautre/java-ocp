import java.io.IOException;
import java.nio.file.*;

public class FileNio2 {
    public static void main(String[] args) {
/*
try(DirectoryStream<Path> listing = Files.newDirectoryStream(chemin, "*.txt")){ … }
//Ne prendra en compte que les fichier ayant l'extension .txt
 */
        Path path = Paths.get("test.txt");
        System.out.println("Chemin absolu du fichier : " + path.toAbsolutePath());
        System.out.println("Est-ce qu'il existe ? " + Files.exists(path));
        System.out.println("Nom du fichier : " + path.getFileName());
        System.out.println("Est-ce un répertoire ? " + Files.isDirectory(path));

        //On récupère maintenant la liste des répertoires dans une collection typée
//Via l'objet FileSystem qui représente le système de fichier de l'OS hébergeant la JVM
        Iterable<Path> roots = FileSystems.getDefault().getRootDirectories();

        //Maintenant, il ne nous reste plus qu'à parcourir
        for (Path chemin : roots) {
            System.out.println(chemin);
            //Pour lister un répertoire, il faut utiliser l'objet DirectoryStream
            //L'objet Files permet de créer ce type d'objet afin de pouvoir l'utiliser
            try (DirectoryStream<Path> listing = Files.newDirectoryStream(chemin)) {

                int i = 0;
                for (Path nom : listing) {
                    System.out.print("\t\t" + ((Files.isDirectory(nom)) ? nom + "/" : nom));
                    i++;
                    if (i % 4 == 0) System.out.println("\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

/*
// copy :
Path source = Paths.get("test.txt");
Path cible = Paths.get("test2.txt");
try {
  Files.copy(source, cible, StandardCopyOption.REPLACE_EXISTING);
} catch (IOException e) { e.printStackTrace();	}

// move :
Path source = Paths.get("test2.txt");
Path cible = Paths.get("test3.txt");
try {
  Files.move(source, cible, StandardCopyOption.REPLACE_EXISTING);
} catch (IOException e) { e.printStackTrace();	}


Path source = Paths.get("test.txt");
//Ouverture en lecture :
try ( InputStream input = Files.newInputStream(source) ) { … }

//Ouverture en écriture :
try ( OutputStream output = Files.newOutputStream(source) )  { … }

//Ouverture d'un Reader en lecture :
try ( BufferedReader reader = Files.newBufferedReader(source, StandardCharsets.UTF_8) )  { … }

//Ouverture d'un Writer en écriture :
try ( BufferedWriter writer = Files.newBufferedWriter(source, StandardCharsets.UTF_8) )  { … }

 */