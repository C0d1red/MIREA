package com.c0d1red.srp;

import com.c0d1red.util.NumberUtil;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SRP {
    private static final String ENCRYPT_ALGORITHM = "SHA-512";
    private static final BigInteger N = calculateN();
    private static final BigInteger g = BigInteger.TWO;
    private static final BigInteger k = BigInteger.valueOf(3);

    public static BigInteger encryptString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(ENCRYPT_ALGORITHM);
            byte[] messageDigest = md.digest(input.getBytes());
            return new BigInteger(1, messageDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static BigInteger calculateM(String I, BigInteger S, BigInteger A, BigInteger B) {
        String encryptedN = encryptString(N.toString()).toString();
        String stringToEncrypt = encryptedN + I + S.toString() + A.toString() + B.toString();
        return encryptString(stringToEncrypt);
    }

    public static BigInteger calculateR(BigInteger A, BigInteger M) {
        String stringToEncrypt = A.toString() + M.toString() + k.toString();
        return encryptString(stringToEncrypt);
    }

    public static BigInteger calculateModPowGN(BigInteger exponent) {
        return g.modPow(exponent, N);
    }

    public static BigInteger calculateB(BigInteger v, BigInteger b) {
        BigInteger multKV = k.multiply(v);
        BigInteger modGN = calculateModPowGN(b);
        return multKV.add(modGN).mod(N);
    }

    public static BigInteger calculateU(BigInteger A, BigInteger B) {
        String stringToEncode = A.add(B).toString();
        return encryptString(stringToEncode);
    }

    public static BigInteger calculateClientGeneralKey(String stringToEncrypt, BigInteger B, BigInteger a, BigInteger u) {
        BigInteger x = encryptString(stringToEncrypt);
        BigInteger modPow = calculateModPowGN(x);
        BigInteger multModPow = k.multiply(modPow);
        BigInteger substractMultModPow = B.subtract(multModPow);
        BigInteger multUX = u.multiply(x);
        BigInteger addAmultUX = a.add(multUX);
        return substractMultModPow.modPow(addAmultUX, N);
    }

    public static BigInteger calculateServerGeneralKey(BigInteger A, BigInteger v, BigInteger u, BigInteger b) {
        BigInteger modPow = v.modPow(u, N);
        BigInteger multModPow = A.multiply(modPow);
        return multModPow.modPow(b, N);
    }

    private static BigInteger calculateN() {
        BigInteger randomPrime = BigInteger.valueOf(NumberUtil.generateRandomPrime());
        return randomPrime.multiply(BigInteger.TWO).add(BigInteger.ONE);
    }

}
