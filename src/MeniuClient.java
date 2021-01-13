import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuClient implements Meniu {
    private final ArrayList<Produs> cos;
    private double sumaTotala;

    MeniuClient() {
        cos = new ArrayList<>();
    }

    @Override
    public void listare() {
        System.out.println("Meniu Client");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = this;

        switch (c) {
            case '1':
                scaneazaProdus();
                break;
            case '2':
                finalizarePlata();
                break;
            case '4':
                totalPlata();
                break;
            case '5':
                stergeProdus();
                break;
            case '6':
                anulareCumparaturi();
                break;
            case '8':
                comutareCasier();
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

    public void scaneazaProdus() {
        System.out.println("Clientul a scanat un produs");
        int produsId = 0;
        float produsCantitate = 0;
        File messages = new File("database/messages.txt");
        try {
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            String[] elemente = line.split(";");
            produsId = Integer.parseInt(elemente[0]);
            produsCantitate = Float.parseFloat(elemente[1]);
            System.out.println("Id-ul meu este: " + produsId);
            System.out.println("Cantintatea mea este: " + produsCantitate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ArrayList<Produs> produse = new ArrayList<>();
        File fisierProduse = new File("database/produse.txt");
        try {
            Scanner scanner = new Scanner(fisierProduse);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(produs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Produs produs : produse) {
            if (produs.getId() == produsId) {
                System.out.println("Am gasit un produs: " + produs.toString());
                produs.setCantitate(produsCantitate);
                cos.add(produs);

                try {
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(produs.toString());
                    scrie.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void totalPlata () {
        System.out.println("Clientul a cerut totalul de plata");
        double suma = 0;
        for (Produs p : cos) {
            suma += p.getPret() * p.getCantitate();
        }
        try {
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(String.valueOf(suma));
            sumaTotala=suma;
            scrie.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void stergeProdus() {
        System.out.println("Clientul a cerut stergerea unui produs");
        File messages = new File("database/messages.txt");
        int id = 0;
        try {
            Scanner scanner = new Scanner(messages);
            String valoare = scanner.nextLine();
            id = Integer.parseInt(valoare);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (Iterator it = cos.iterator(); it.hasNext(); ) {
            Produs p = (Produs) it.next();
            if (p.getId() == id) {
                it.remove();
                try {
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(true));
                    scrie.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private void anulareCumparaturi() {
        System.out.println("Clientul a cerut anularea cumparaturilor");
        cos.clear();
        try {
            FileWriter scrie =  new FileWriter("database/output.txt");
            scrie.write(String.valueOf(true));
            scrie.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private float totalSuma() {
        float suma = 0;
        for (Produs p : cos) {
            suma += p.getPret() * p.getCantitate();
        }
        return suma;
    }

    private void finalizarePlata() {
        System.out.println("Clientul a cerut finalizare plata");

        File mesaj = new File("database/messages.txt");
        float valoare = 0;
        try {
            Scanner scanner = new Scanner(mesaj);
            String line = scanner.nextLine();
            valoare = Float.parseFloat(line);
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        sumaTotala -= valoare;
        try {
            File fisier = new File("database/output.txt");
            FileWriter write = new FileWriter(fisier);
            write.write(String.valueOf(sumaTotala));
            write.close();
        } catch (Exception ex) {
            ex.getStackTrace();
        }
        if (sumaTotala <= 0) {
            try {
                File fisier = new File("database/vanzari.txt");
                FileWriter write = new FileWriter(fisier,true);
                write.append(String.valueOf(totalSuma())).append("\n");
                cos.clear();
                write.close();
            } catch(Exception ex){
                ex.getStackTrace();
            }
        }
    }

    private void comutareCasier() {
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

    private void comutareAdmin() {
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