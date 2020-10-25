package com.c0d1red.rsa;

public class PublicKey {
    private final int MOD;
    private final int E;

    public PublicKey(int MOD, int e) {
        this.MOD = MOD;
        this.E = e;
    }

    public int getMOD() {
        return MOD;
    }

    public int getE() {
        return E;
    }

    @Override
    public String toString() {
        return "PublicKey{" +
                "mod=" + MOD +
                ", e=" + E +
                '}';
    }
}
