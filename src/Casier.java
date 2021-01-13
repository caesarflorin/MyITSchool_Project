public class Casier {
    private String user;
    private String parola;

    public Casier(String user, String parola) {
        this.user = user;
        this.parola = parola;
    }

    public String getUser() {
        return user;
    }

    public String getParola() {
        return parola;
    }

    public boolean equals(Object o) {
        if (! (o instanceof Casier)) {
            return false;
        }

        Casier c = (Casier) o;

        return this.user.equals((c.user)) && this.parola.equals(c.parola);
    }

    public String toString() {
        return user + ";" + parola + "\n";
    }
}
