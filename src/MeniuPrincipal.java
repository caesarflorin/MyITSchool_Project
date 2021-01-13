public class MeniuPrincipal implements Meniu {
    @Override
    public void listare() {
        System.out.println("Meniu principal");
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = null;

        switch (c) {
            case 'c':
                meniu = new MeniuClient();
                break;
            case 'a':
                meniu = new MeniuAdmin();
                break;
            case 'm':
                meniu = new MeniuCasier();
                break;
            default:
                System.out.println("Meniu invalid!");
        }

        return meniu;
    }
}
