package com.hypermarket;

import java.io.File;
import java.io.FileWriter;

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
