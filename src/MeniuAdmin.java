import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuAdmin extends MeniuAngajat {
    private double totalVanzari;

    @Override
    public void listare() {
        System.out.println("Meniu admin");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = this;

        switch (c) {
            case '1':
                adauga();
                break;
            case '2':
                sterge();
                break;
            case '3':
                afisareCasier();
                break;
            case '4':
                totalVanzari();
                break;
            case '8':
                comutareCasier();
                break;
            case '0':
                meniu = new MeniuPrincipal();
                break;
            default:
                System.out.println(this.getClass().getName() + " - Optiune invalida");
        }
        return meniu;
    }

    @Override
    public void adauga() {
        System.out.println("Am intrat in adauga casier");
        Casier casier = null;
        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            String[] elemente = line.split(";");
            String user = elemente[0];
            String pass = elemente[1];
            casier = new Casier(user, pass);
            System.out.println("Casierul din message: " + casier.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ArrayList<Casier> casieri = new ArrayList<>();
        File casieriFile = new File("database/casieri.txt");
        try {
            Scanner scanner = new Scanner(casieriFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String user = elemente[0];
                String pass = elemente[1];
                Casier c = new Casier(user, pass);
                casieri.add(c);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        boolean rezultat = false;
        boolean found = false;
        for (Casier c: casieri) {
            if (c.equals(casier)) {
                found = true;
                break;
            }
        }
        if (! found) {
            try {
                FileWriter writer = new FileWriter(casieriFile, true);
                assert casier != null;
                writer.write(casier.toString());
                writer.close();
                rezultat = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Rezultat este: " + rezultat);
        try {
            File output = new File("database/output.txt");
            FileWriter writer = new FileWriter(output);
            writer.write(String.valueOf(rezultat));
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void afisareCasier() {
        System.out.println("Meniu Admin");
        File fisierCasieri = new File("database/casieri.txt");
        ArrayList<Casier> casieri = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(fisierCasieri);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String u = elemente[0];
                String p = elemente[1];
                Casier c = new Casier(u, p);
                casieri.add(c);
                try {
                    FileWriter scrie = new FileWriter("database/output.txt");
                    for (Casier casier : casieri) {
                        scrie.write(casier.toString());
                    }
                    scrie.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge() {
        ArrayList<Casier> casieri = new ArrayList<>();
        System.out.println("Am sters casierul: ");
        File messages = new File("database/messages.txt");
        String user = "";
        try {
            Scanner scanner = new Scanner(messages);
            String valoare = scanner.nextLine();
            user = String.valueOf(valoare);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File fisierCasieri = new File("database/casieri.txt");
        try {
            Scanner scanner = new Scanner(fisierCasieri);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String u = elemente[0];
                String p = elemente[1];
                Casier c = new Casier(u, p);
                casieri.add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Iterator it = casieri.iterator(); it.hasNext(); ) {
            Casier c = (Casier) it.next();
            if (c.getUser().equals(user)) {
                it.remove();
                try {
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(true));
                    scrie.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter write = new FileWriter("database/casieri.txt");
                    for (Casier casier : casieri) {
                        write.write(casier.toString());
                    }
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void comutareCasier() {
        System.out.println("Cautare si verificare casier");

        String user = null, pass = null;
        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            String[] elemente = line.split(";");
            user = elemente[0];
            pass = elemente[1];
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        File fisierCasieri = new File("database/casieri.txt");
        try {
            Scanner scanner = new Scanner(fisierCasieri);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");
                String u = elemente[0];
                String p = elemente[1];
                if (u.equals(user) && p.equals(pass)) {
                    try {
                        FileWriter scrie = new FileWriter("database/output.txt");
                        scrie.write(String.valueOf(true));
                        scrie.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void totalVanzari() {
        System.out.println("Adminul a cerut afisare total vanzari");

        File fisierVanzari = new File("database/vanzari.txt");
        double vanzari;
        try {
            Scanner citire = new Scanner(fisierVanzari);
            while (citire.hasNextLine()) {
                String valoare = citire.nextLine();
                vanzari = Float.parseFloat(valoare);
                totalVanzari += vanzari;
                System.out.println("Total vanzari este: " + totalVanzari);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(String.valueOf(totalVanzari));
            scrie.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}