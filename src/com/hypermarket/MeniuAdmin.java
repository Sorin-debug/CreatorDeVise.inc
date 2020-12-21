package com.hypermarket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MeniuAdmin extends MeniuAngajat {

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
                listare();
                break;
            case '4':
                afisare();
                break;
            case '8':
                comutareClient();
                break;
            case '0':
                meniu = new MeniuPrincipal();
                break;
            default:
                System.out.println(this.getClass().getName() + " - Optiune invalida");
                break;
        }

        return meniu;
    }

    @Override
    public void afisare() {

    double totalSum =0;

        File fisierProduse = new File("database/vanzari.txt");
        try {
            // 1st try
            Scanner scanner = new Scanner(fisierProduse);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                double a = Double.parseDouble(line);
                totalSum = totalSum + a;
            }

                try {
                    //2nd try
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(totalSum));
                    scrie.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    // end of afisare method

    }


    @Override
    public void comutareClient() {
        System.out.println("Cautare si verificare casier");
        ArrayList<Casier> casieri = new ArrayList<>();

        File casier = new File("database/casieri.txt");
        try {
            //1st try
            Scanner s = new Scanner(casier);
            while (s.hasNextLine()){
                String line = s.nextLine();
                String[] elemente = line.split(";");

                String user = elemente[0];
                String parola = elemente[1];

                Casier casier1 = new Casier(user, parola);
                casieri.add(casier1);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File mesaj = new File("database/messages.txt");
        try {
            // 2nd try
            Scanner s = new Scanner(mesaj);
            String line = s.nextLine();
            String[] elemente = line.split(";");

            String user = elemente[0];
            String parola = elemente[1];

            Casier casier1 = new Casier(user, parola);
            String raspuns = "false";

            for (Casier c : casieri) {
                if (casier1.equals(c)) {
                    raspuns = "true";
                    break;
                }
            }
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(raspuns);
            scrie.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    // end of comutareClient method
    }

    @Override
    public void adauga() {
        //adaugam casieri
        File readCasieri = new File("database/messages.txt");
        try {
            Scanner rc = new Scanner(readCasieri);
            String line = rc.nextLine();

            FileWriter scrieCas = new FileWriter("database/casieri.txt",true);
            scrieCas.write( line + "\n");
            scrieCas.close();
            FileWriter confirmaCasier = new FileWriter("database/output.txt");
            confirmaCasier.write(String.valueOf(true));
            confirmaCasier.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    //end of adauga method
    }

    @Override
    public void listare() {
        //start
        System.out.println("Afisare casieri");
        // dam clear la output
        File messages = new File("database/casieri.txt");
        try {
            //1st try
            FileWriter ClearOutput = new FileWriter("database/output.txt");
            ClearOutput.write("");
            ClearOutput.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //populam output cu user
            try {
                //2nd try
                Scanner scanner = new Scanner(messages);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    try {
                        FileWriter scrieCasieri = new FileWriter("database/output.txt", true);
                        scrieCasieri.write(line + "\n");
                        scrieCasieri.close();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                } catch(Exception ex){
                    ex.printStackTrace();
                }

    // end of listare method
    }

    @Override
    public void sterge() {

    System.out.println("Cream lista noua");

        ArrayList<Casier> casieri = new ArrayList<>(); // array-ul va fi gol

        File listaCasieri = new File("database/casieri.txt");
        try {
            //1st try
            Scanner scanner = new Scanner(listaCasieri);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                String user = elemente[0];
                String parola = elemente[1];

                Casier casierMaster = new Casier(user,parola);
                casieri.add(casierMaster);
                }
            }catch (Exception ex) {
            ex.printStackTrace();
        }

        // stabilim userul de sters si il salvam in variabila ajutatoare
        String userDeSters="";
        File messages = new File("database/messages.txt");
        try {
            //2nd try
            Scanner scanner = new Scanner(messages);
             userDeSters = scanner.nextLine();
            System.out.println("Id-ul produsului de sters este: " + userDeSters);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // populam cu casieri

              try {
                    //3rd try
                    FileWriter scrie = new FileWriter("database/casieri.txt");
                    for (Casier x: casieri) {
                        // fiecare casier care nu este egal cu cel de sters
                        if(!(x.getUser().equals(userDeSters))) {
                            scrie.write(x.toString());
                            FileWriter confirmaDeleteUser = new FileWriter("database/output.txt");
                            confirmaDeleteUser.write(String.valueOf("true"));
                            confirmaDeleteUser.close();

                    }else{ System.out.println("Userul: " + x.getUser() + " a fost sters");}

                }
                    // inchidem fisierul unde am scris casierii
                  scrie.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

    //end of sterge method

    }


    //end of meniu angajat
}