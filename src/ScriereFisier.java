import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ScriereFisier {
    private File fisier;

    public ScriereFisier(File fisier) {
        this.fisier = fisier;
    }

    public void scrieCaracter(char c) {
        try {
            FileWriter scriere = new FileWriter(fisier);
            scriere.write(c);
            scriere.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


