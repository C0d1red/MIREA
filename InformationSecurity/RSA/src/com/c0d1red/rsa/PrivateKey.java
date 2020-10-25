package com.c0d1red.rsa;

public class PrivateKey {
    private final int MOD;
    private final int D;

    public PrivateKey(int MOD, int d) {
        this.MOD = MOD;
        this.D = d;
    }

    public int getMOD() {
        return MOD;
    }

    public int getD() {
        return D;
    }

    @Override
    public String toString() {
        return "PrivateKey{" +
                "mod=" + MOD +
                ", d=" + D +
                '}';
    }

}
