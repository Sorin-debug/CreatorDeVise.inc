package com.hypermarket;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MeniuClient implements Meniu {


    private ArrayList<Produs> cos;
    MeniuClient() {
        cos = new ArrayList<>();
    }

    private boolean AmPlatit = true;
    private double sumaDePlata = 0;

    @Override
    public void afisare() {
        System.out.println("com.hipermarket.Meniu Client");
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
                verificareCasier();
                break;
            case '9':
                verificareAdmin();
                break;
            case '0':
                meniu = new MeniuPrincipal();
                break;
            default:
                System.out.println(this.getClass().getName() + " - Optiune invalida");
        }

        return meniu;
    }

    private void scaneazaProdus() {
        System.out.println("Clientul a scanat un produs");
        int produsId = 0;
        float produsCantitate = 0;

        File messages = new File("database/messages.txt");
        try {
            //1st try
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

        ArrayList<Produs> produse = new ArrayList<>(); // array-ul va fi gol

        File fisierProduse = new File("database/produse.txt");
        try {
            //2nd try
            Scanner scanner = new Scanner(fisierProduse);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate;
                if( produsCantitate > 0){
                    cantitate = produsCantitate;
                }else {
                     cantitate = Float.parseFloat(elemente[3]);
                }
                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produs = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produse.add(produs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Produs produs: produse) {
            if (produs.getId() == produsId) {
                System.out.println("Am gasit un produs: " + produs.toString());
                cos.add(produs);

                try {
                    //3rd try
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(produs.toString());
                    scrie.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    private double totalPlata () {
        System.out.println("Clientul a cerut totalul de plata");

        // Aici facem suma tututror produselor din cos
        double dePlata = 0;
        System.out.println("Suma inainte de cos e "+ dePlata);
        for(Produs c : cos){
            dePlata += c.getPret() * c.getCantitate();
            System.out.println("DePlata din loop" + dePlata);
            System.out.println("Produs din cos: "+  c.getNume());
        }

        System.out.println("Suma dePlata este: " + dePlata);

        // Scriem in output suma totala de plata
        try {
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(String.valueOf(dePlata));
            scrie.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        AmPlatit = true;
        return dePlata;
    }


    private void finalizarePlata() {


        try {
            //1st try
            double SumaIntrodusa = 0;

            if(AmPlatit) {
                System.out.println("Clientul a cerut finalizare plata");

                // salvam  totalul de plata sa il folosim
                 sumaDePlata = totalPlata();
                 System.out.println("SumaDePlata: " + sumaDePlata);

                // salvam suma totala de plata in vanzari
                FileWriter scrieVanzare = new FileWriter("database/vanzari.txt", true);
                scrieVanzare.write(String.valueOf(sumaDePlata) + "\n");
                scrieVanzare.close();
                AmPlatit = false;
            }


            // citim suma introdusa din messages.txt
            File messages = new File("database/messages.txt");
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            SumaIntrodusa = Double.parseDouble(line);
            System.out.println("Suma introdusa de plata este: " + SumaIntrodusa);

            sumaDePlata = sumaDePlata - SumaIntrodusa;
            // pentru confirmarea platii introducem diferenta

                try {
                    //2nd try
                    FileWriter scrie = new FileWriter("database/output.txt");
                    scrie.write(String.valueOf(sumaDePlata));
                    scrie.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

               if(sumaDePlata > 0){
                    System.out.println("Suma de plata din if : " + sumaDePlata);
                   FileWriter rescrie = new FileWriter("database/messages.txt");
                   rescrie.write(String.valueOf(sumaDePlata));
                   rescrie.close();

               }else{
                   // daca s-a facut plata dam clear la cos
                   cos.clear();
               }



        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void stergeProdus() {
        System.out.println("Clientul a cerut stergerea unui produs");

        boolean sterge = false;
        int idSters = 0;

        File messages = new File("database/messages.txt");
        try {
            //1st try
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            idSters = Integer.parseInt(line);
            System.out.println("Id-ul produsului de sters este: " + idSters);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        // cautam id-ul de sters si setam sterge pe true

        for (Iterator it = cos.iterator(); it.hasNext();) {

            Produs c = (Produs)it.next();
            if (c.getId() == idSters ) {
                // stergem produsul din cos
                it.remove();
                sterge = true;
            }else {System.out.println("Id produsului nu a fost gasit");}


        }


        try {
            //2nd try
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(String.valueOf(sterge) );
            scrie.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void anulareCumparaturi() {
        System.out.println("Clientul a cerut anularea cumparaturilor");
        // Dam clear la cos sa golim arrayul dupa anulare
        cos.clear();

        boolean anulare = true;
        try {
            FileWriter scrie = new FileWriter("database/output.txt");
            scrie.write(String.valueOf(anulare) );
            scrie.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void verificareCasier() {
        System.out.println("Cautare si verificare casier");
        ArrayList<Casier> casieri = new ArrayList<>();

        File casier = new File("database/casieri.txt");
        try {
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
    }

    private void verificareAdmin() {
        System.out.println("Cautare si verificare admin x   ");

        ArrayList<Admin> adminList = new ArrayList<>();

        File admin = new File("database/admin.txt");
        try {
            Scanner s = new Scanner(admin);

            while (s.hasNextLine()){
                String line = s.nextLine();
                String[] elemente = line.split(";");

                String user = elemente[0];
                String parola = elemente[1];

                Admin admin1 = new Admin(user, parola);
                adminList.add(admin1);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        File mesaj = new File("database/messages.txt");
        try {
            Scanner s = new Scanner(mesaj);
            String line = s.nextLine();
            String[] elemente = line.split(";");

            String user = elemente[0];
            String parola = elemente[1];

            Admin admin1 = new Admin(user, parola);
            String raspuns = "false";

            for (Admin c : adminList) {
                if (admin1.equals(c)) {
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
    }




}