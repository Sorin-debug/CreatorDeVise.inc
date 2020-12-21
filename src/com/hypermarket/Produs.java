package com.hypermarket;


public class Produs {
    private final int id;
    private final String nume;
    private final float pret;
    private final float cantitate;
    private final TipCantitate tipCantitate;
    private final CategorieProdus categorie;

    public Produs(int id, String nume, float pret, float cantitate, TipCantitate tipCantitate, CategorieProdus categorie) {
        this.id = id;
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.tipCantitate = tipCantitate;
        this.categorie = categorie;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public float getPret() {
        return pret;
    }

    public float getCantitate() {
        return cantitate;
    }

    public TipCantitate getTipCantitate() {
        return tipCantitate;
    }

    public CategorieProdus getCategorie() {
        return categorie;
    }



    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Produs)) {
            return false;
        }

        Produs p = (Produs)obj;
        return this.id == p.id &&
                this.nume.equals(p.nume) &&
                this.pret == p.pret &&
                this.cantitate == p.cantitate &&
                this.tipCantitate.equals(p.tipCantitate) &&
                this.categorie.equals(p.categorie);
    }

    @Override
    public String toString() {
        return String.valueOf(id) + ";" + nume + ";" + pret + ";" + cantitate + ";" + tipCantitate.ordinal() + ";"
                + categorie.ordinal() + "\n";
    }
}