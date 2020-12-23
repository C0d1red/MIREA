package com.c0d1red.srp;

import com.c0d1red.util.NumberUtil;

import java.math.BigInteger;

public class Server {
    private String clientUsername;
    private BigInteger clientV;
    private BigInteger B;
    private BigInteger b;
    private BigInteger u;
    private BigInteger S;
    private BigInteger generalKey;
    private BigInteger M;
    private BigInteger R;

    public BigInteger getB() {
        return B;
    }

    public BigInteger getGeneralKey() {
        return generalKey;
    }

    public BigInteger getM() {
        return M;
    }

    public BigInteger getR() {
        return R;
    }

    public void register(String clientUsername, BigInteger clientV) {
        this.clientUsername = clientUsername;
        this.clientV = clientV;
    }

    public void generateRandomNumber() {
        int randomNumber = NumberUtil.generateRandomNum();
        b = BigInteger.valueOf(randomNumber);
    }

    public void calculateB() {
        B = SRP.calculateB(clientV, b);
    }

    public void verifyA(BigInteger A) {
        if (A.equals(BigInteger.ZERO)) {
            throw new RuntimeException("A is zero");
        }
    }

    public void calculateU(BigInteger A) {
        u = SRP.calculateU(A, B);
    }

    public void calculateGeneralKey(BigInteger A) {
        S = SRP.calculateServerGeneralKey(A, clientV, u, b);
        generalKey = SRP.encryptString(S.toString());
    }

    public void calculateM(BigInteger A) {
        M = SRP.calculateM(clientUsername, S, A, B);
    }

    public void calculateR(BigInteger A) {
        R = SRP.calculateR(A, M);
    }

}
