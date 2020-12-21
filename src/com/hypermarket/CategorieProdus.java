package com.hypermarket;

public enum CategorieProdus {
    FRUCTE,
    LEGUME,
    MEZELURI,
    LACTATE,
    PANIFICATIE,
    CURATENIE,
    BAUTURI;

    public static CategorieProdus fromInt(int id) {
        switch (id) {
            case 0:
                return CategorieProdus.FRUCTE;
            case 1:
                return CategorieProdus.LEGUME;
            case 2:
                return CategorieProdus.MEZELURI;
            case 3:
                return CategorieProdus.LACTATE;
            case 4:
                return CategorieProdus.PANIFICATIE;
            case 5:
                return CategorieProdus.CURATENIE;
            case 6:
                return CategorieProdus.BAUTURI;
        }

        return CategorieProdus.FRUCTE;
    }

}
