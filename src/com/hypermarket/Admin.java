package com.hypermarket;

public class Admin {
    private String user;
    private String parola;

    public Admin(String user, String parola) {
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
        if (! (obj instanceof Admin)) {
            return false;
        }
        Admin c = (Admin)obj;
        return this.user.equals(c.user) && this.parola.equals(c.parola);
    }
}
