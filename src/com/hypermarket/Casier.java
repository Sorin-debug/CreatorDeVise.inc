package com.hypermarket;


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

    @Override
    public String toString() {
        return user + ";" + parola + "\n";
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Casier)) {
            return false;
        }
        Casier c = (Casier)obj;
        return this.user.equals(c.user) && this.parola.equals(c.parola);
    }
}
