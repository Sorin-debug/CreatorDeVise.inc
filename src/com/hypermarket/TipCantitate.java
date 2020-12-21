package com.hypermarket;

public enum TipCantitate {
    KG,
    L,
    BUC;

    public static TipCantitate fromInt(int id) {
        switch (id) {
            case 0:
                return TipCantitate.KG;
            case 1:
                return TipCantitate.L;
            case 2:
                return TipCantitate.BUC;
        }

        return TipCantitate.KG;
    }
}
