package com.c0d1red.rsa;

import com.c0d1red.primenumber.PrimeNumberGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RSA {
    public static final int MIN_PRIME_NUMBER = RSAMapper.getAlphabetLength();
    public static final int MAX_PRIME_NUMBER = 300;
    private int p;
    private int q;
    private int mod;
    private int e;
    private int d;

    public RSA() {
        generatePQ();
        calculateParameters();
    }

    public void setP(int p) {
        this.p = p;
        calculateParameters();
    }

    public void setQ(int q) {
        this.q = q;
        calculateParameters();
    }

    public PublicKey createPublicKey() {
        return new PublicKey(mod, e);
    }

    public PrivateKey createPrivateKey() {
        return new PrivateKey(mod, d);
    }

    public String encrypt(String term, PublicKey publicKey) {
        RSAMapper rsaMapper = new RSAMapper();
        List<BigInteger> numbersTerm = rsaMapper.termToNumbers(term);
        List<BigInteger> encryptedNumbersTerm = translate(numbersTerm, publicKey.getE(), publicKey.getMOD());
        return rsaMapper.numbersToString(encryptedNumbersTerm);
    }

    public String decrypt(String encryptedNumbersTerm, PrivateKey privateKey) {
        RSAMapper rsaMapper = new RSAMapper();
        List<BigInteger> encryptedNumbers = rsaMapper.stringToNumbers(encryptedNumbersTerm);
        List<BigInteger> decryptedNumbersTerm = translate(encryptedNumbers, privateKey.getD(), privateKey.getMOD());
        return rsaMapper.numbersToTerm(decryptedNumbersTerm);
    }

    private void generatePQ() {
        p = PrimeNumberGenerator.generate(MIN_PRIME_NUMBER, MAX_PRIME_NUMBER);
        q = PrimeNumberGenerator.generateWithExclude(MIN_PRIME_NUMBER, MAX_PRIME_NUMBER, p);
    }

    private List<BigInteger> translate(List<BigInteger> numbers, int degree, int mod) {
        List<BigInteger> translatedList = new ArrayList<>();
        for (BigInteger number : numbers) {
            BigInteger powBI = number.pow(degree);
            BigInteger modBI = BigInteger.valueOf(mod);
            BigInteger resultBI = powBI.mod(modBI);
            translatedList.add(resultBI);
        }
        return translatedList;
    }

    private void calculateParameters() {
        mod = calculateMod(p, q);
        int fi = calculateFi(p, q);
        e = generateE(fi);
        d = calculateD(e, fi);
    }

    private int calculateMod(int p, int q) {
        return p * q;
    }

    private int calculateFi(int p, int q) {
        return (p - 1) * (q - 1);
    }

    private int generateE(int fi) {
        return PrimeNumberGenerator.generateWithDivideCheck(MIN_PRIME_NUMBER, fi, fi);
    }

    private int calculateD(int e, int fi) {
        int d = 1;
        boolean isCorrectD = (d * e) % fi == 1;
        while (!isCorrectD) {
            d++;
            isCorrectD = (d * e) % fi == 1;
        }
        return d;
    }

}
