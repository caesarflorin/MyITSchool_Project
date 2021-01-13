import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuCasier extends MeniuAngajat {

    @Override
    public void listare() {
        System.out.println("Meniu Casier");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = this;

        switch (c) {
            case '1':
                adauga();
                break;
            case '2':
                afisareCategorie();
                break;
            case '3':
                sterge();
                break;
            case '9':
                comutareAdmin();
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
        System.out.println("Casierul a adaugat un produs");
        Produs produs = null;
        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            String[] elemente = line.split(";");

            int id = Integer.parseInt(elemente[0]);
            String nume = elemente[1];
            float pret = Float.parseFloat(elemente[2]);
            float cantitate = Float.parseFloat(elemente[3]);
            TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
            CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

            produs = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
            System.out.println("Produsul din mesaje: " + produs.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Produs> produse = new ArrayList<>();
        File produseFile = new File("database/produse.txt");
        try {
            Scanner scanner = new Scanner(produseFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs p = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        boolean rezultat = false;
        boolean found = false;
        for (Produs p : produse) {
            if (p.equals(produs)) {
                found = true;
                break;
            }
        }
        if (! found) {
            try {
                FileWriter writer = new FileWriter(produseFile, true);
                assert produs != null;
                writer.write(produs.toString());
                writer.close();
                rezultat = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Rezultat este: " + rezultat);
        try {
            File output = new File("database/output.txt");
            FileWriter writer = new FileWriter(output);
            writer.write(String.valueOf(rezultat));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void afisareCategorie() {
        File messages = new File("database/messages.txt");
        ArrayList<Produs> produse =  new ArrayList<>();
        CategorieProdus categorieProdus = null;
        try {
            Scanner scanner = new Scanner(messages);
            while (scanner.hasNextLine()) {
                String valoare = scanner.nextLine();
                categorieProdus = CategorieProdus.valueOf(valoare);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File fisierProduse = new File("database/produse.txt");
        try {
            Scanner citire = new Scanner(fisierProduse);
            while (citire.hasNextLine()) {
                String line = citire.nextLine();
                String[] elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus cp = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs p = new Produs(id, nume, pret, cantitate, tipCantitate, cp);
                produse.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            FileWriter scrie = new FileWriter("database/output.txt");
        for (Produs produs : produse) {
            if (produs.getCategorieProdus().equals(categorieProdus)) {
                    scrie.write(produs.toString());
                }
            }
            scrie.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sterge() {
        ArrayList<Produs> produse = new ArrayList<>();
        System.out.println("Casierul a sters un produs");
        File messages = new File("database/messages.txt");
        int id = 0;
        try {
            Scanner scanner = new Scanner(messages);
            String valoare = scanner.nextLine();
            id = Integer.parseInt(valoare);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File fisierProduse = new File("database/produse.txt");
        try {
            Scanner scanner = new Scanner(fisierProduse);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                int i = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs p = new Produs(i, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(p);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Iterator it = produse.iterator(); it.hasNext(); ) {
            Produs p = (Produs) it.next();
            if (p.getId() == id) {
                it.remove();
                try {
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(true));
                    scrie.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    FileWriter write = new FileWriter("database/produse.txt");
                    for (Produs produs : produse) {
                        write.write(produs.toString());
                    }
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void comutareAdmin() {
        System.out.println("Cautare si verificare admin");
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
        File fisierAdmin = new File("database/admin.txt");
        try {
            Scanner scanner = new Scanner(fisierAdmin);
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
}