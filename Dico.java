
import java.util.ArrayList;
import java.io.*;
import java.util.Scanner;

public class Dico {

    private ArrayList<String> listeMots;

    public Dico() {
        this.listeMots = new ArrayList<>();
    }

    public Dico(String cheminAcces) {
        this();
        this.nourrir(cheminAcces);
    }

    public void nourrir(String chemin) {
        try {

            FileInputStream file = new FileInputStream(chemin);
            Scanner scanner = new Scanner(file);

            String courant = scanner.nextLine();

            while (scanner.hasNextLine()) {

                courant = scanner.nextLine();
                this.listeMots.add(courant);

            }

            scanner.close();

        }

        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean contient(String mot){
        return this.listeMots.contains(mot);
    }
}
