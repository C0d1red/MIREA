package com.c0d1red.srp;

import com.c0d1red.util.NumberUtil;
import com.c0d1red.util.StringUtil;

import java.math.BigInteger;

public class Client {
    private final String username;
    private final String password;
    private String salt;
    private BigInteger v;
    private BigInteger a;
    private BigInteger A;
    private BigInteger u;
    private BigInteger S;
    private BigInteger generalKey;
    private BigInteger m;
    private BigInteger r;

    public Client(String username, String password) {
        this.username = username;
        this.password = password;
        calculateStartParameters();
    }

    public String getUsername() {
        return username;
    }

    public BigInteger getV() {
        return v;
    }

    public BigInteger getA() {
        return A;
    }

    public BigInteger getM() {
        return m;
    }

    public BigInteger getR() {
        return r;
    }

    public BigInteger getGeneralKey() {
        return generalKey;
    }

    public void generateRandomNumber() {
        int randomNumber = NumberUtil.generateRandomNum();
        a = BigInteger.valueOf(randomNumber);
    }

    public void calculateA() {
        A = SRP.calculateModPowGN(a);
    }

    public void calculateGeneralKey(BigInteger B) {
        String stringToEncrypt = salt + password;
        S = SRP.calculateClientGeneralKey(stringToEncrypt, B, a, u);
        generalKey = SRP.encryptString(S.toString());
    }

    public void calculateU(BigInteger B) {
        u = SRP.calculateU(A, B);
    }

    public void verifyB(BigInteger B) {
        if (B.equals(BigInteger.ZERO)) {
            throw new RuntimeException("B is zero");
        }
    }

    public void calculateM(BigInteger B) {
        m = SRP.calculateM(username, S, A, B);
    }

    public void calculateR() {
        r = SRP.calculateR(A, m);
    }

    private void calculateStartParameters() {
        salt = StringUtil.generateRandomString();
        BigInteger x = SRP.encryptString(salt + password);
        v = SRP.calculateModPowGN(x);
    }

}
