package com.hypermarket;

import java.io.File;
import java.util.Scanner;

public class CitireFisier {
    private File fisier;
    public CitireFisier(File fisier) {
        this.fisier = fisier;
    }

    public char citireCaracter() {
        char rezultat;
        try {
            Scanner citire = new Scanner(fisier);
            String line = citire.nextLine();
            rezultat = line.charAt(0);
            citire.close();
        } catch (Exception e) {
            rezultat = 'x';
        }

        return rezultat;
    }

}

