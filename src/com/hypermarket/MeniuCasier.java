package com.hypermarket;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MeniuCasier extends MeniuAngajat {

    private ArrayList<Produs> listaProduse;
    MeniuCasier() {
        listaProduse = new ArrayList<>();
    }

    @Override
    public void afisare() {
    }

    @Override
    public Meniu interpreteazaComanda(char c) {
        Meniu meniu = this;

        switch (c) {
            case '1':
                adauga();
                break;
            case '2':
                listare();
                break;
            case '3':
                sterge();
                break;
            case '9':
                comutareClient();
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
    public void comutareClient() {
        System.out.println("Cautare si verificare admin x   ");

        ArrayList<Admin> adminList = new ArrayList<>();
        File admin = new File("database/admin.txt");

        try {
            //1st try
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
            //2nd try
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

    @Override
    public void adauga() {

        File messages = new File("database/messages.txt");
        try {
            //1st try
            Scanner scanner = new Scanner(messages);
            String line = scanner.nextLine();
            System.out.println("Id-ul produsului de adaugat este: " + line);

                try {
                    //2nd try
                    FileWriter scrie = new FileWriter("database/produse.txt",true);
                    scrie.write( String.valueOf(line) + "\n" );
                    scrie.close();
                    FileWriter confirmaProdus = new FileWriter("database/output.txt");
                    confirmaProdus.write(String.valueOf(true));
                    confirmaProdus.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // end of adauga method
    }

    @Override
    public void listare() {

        String categorie = "";
        File messages = new File("database/messages.txt");
        try {
            //1st try
            Scanner scanner = new Scanner(messages);
            categorie = scanner.nextLine();
            System.out.println("Id-ul meu este: " + categorie);

            ArrayList<Produs> produse = new ArrayList<>(); // array-ul va fi gol

            File fisierProduse = new File("database/produse.txt");

            try {
                //2nd try
                Scanner FP = new Scanner(fisierProduse);

                while (FP.hasNextLine()) {
                    String line = FP.nextLine();
                    String[] elemente = line.split(";");

                    int id = Integer.parseInt(elemente[0]);
                    String nume = elemente[1];
                    float pret = Float.parseFloat(elemente[2]);
                    float cantitate = Float.parseFloat(elemente[3]);
                    TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                    CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                    Produs produs = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);

                        // adaugam inloop doar produsele din categoria introdusa
                    if(categorie.equals(produs.getCategorie().name()) ) {
                        System.out.println("Am reusit sa adaug un produs din categoria : " + produs.getNume());
                        produse.add(produs);
                    }


                            try {
                                //3rd try
                                FileWriter xxx = new FileWriter("database/output.txt");
                                for (Produs x: produse) {
                                xxx.write(x.toString() + "\n");
                            }
                                //inchidem fisierul dupa ce am introdus produsele din array
                                xxx.close();
                            }  catch (IOException ex) {
                                ex.printStackTrace();
                            }

                   //end of for loop

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    //end of code listare


    }

    @Override
    public void sterge() {

        ArrayList<Produs> produseNoi = new ArrayList<>(); // array-ul va fi gol

        File listaProduse = new File("database/produse.txt");
        try {
            //1st try
            Scanner scanner = new Scanner(listaProduse);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] elemente = line.split(";");

                int id = Integer.parseInt(elemente[0]);
                String nume = elemente[1];
                float pret = Float.parseFloat(elemente[2]);
                float cantitate = Float.parseFloat(elemente[3]);

                TipCantitate tipCantitate = TipCantitate.fromInt(Integer.parseInt(elemente[4]));
                CategorieProdus categorieProdus = CategorieProdus.fromInt(Integer.parseInt(elemente[5]));

                Produs produse = new Produs(id, nume, pret, cantitate, tipCantitate, categorieProdus);
                produseNoi.add(produse);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }

        // stabilim produsul de sters si il salvam in variabila ajutatoare
        String produsDeSters="";
        File messages = new File("database/messages.txt");
        try {
            //2nd try
            Scanner scanner = new Scanner(messages);
            produsDeSters = scanner.nextLine();
            System.out.println("Id-ul produsului de sters este: " + produsDeSters);

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        // populam

                try {
                    //4th try
                    int idProdus =   Integer.parseInt(produsDeSters);
                    FileWriter scrie = new FileWriter("database/produse.txt");
                    for (Produs x: produseNoi) {


                    if(x.getId() != idProdus ) {
                    scrie.write(x.toString());
                    FileWriter confirmaDeleteUser = new FileWriter("database/output.txt");
                    confirmaDeleteUser.write(String.valueOf("true") );
                    confirmaDeleteUser.close();

                }else{System.out.println("Produsul cu userul: " + x.getId() + " a fost sters");}
                    //end for loop
                }
                    scrie.close();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

//end of sterge
    }


}