package com.c0d1red.dh;

import com.c0d1red.NumberGenerator;

import java.math.BigInteger;

import static com.c0d1red.NumberGenerator.generatePrimitiveRootFor;

public class DH {
    private int p;
    private int g;

    public int generatePrivateKey(){
        return NumberGenerator.generateRandomNatural();
    }

    public int generateP(){
        p = NumberGenerator.generateRandomPrime();
        return p;
    }

    public int generateG(){
        g = generatePrimitiveRootFor(p);
        return g;
    }

    public int calculatePersonalPublicKey(int privateKey){
        BigInteger pBD = BigInteger.valueOf(p);
        BigInteger gBD = BigInteger.valueOf(g);
        BigInteger personalPublicKey = gBD.pow(privateKey).remainder(pBD);
        return personalPublicKey.intValue();
    }

    public int calculateGeneralSecretKey(int personalPublicKey, int privateKey){
        BigInteger personalPublicKeyBD = BigInteger.valueOf(personalPublicKey);
        BigInteger pBD = BigInteger.valueOf(p);
        BigInteger generalKey = personalPublicKeyBD.pow(privateKey).remainder(pBD);
        return generalKey.intValue();
    }

}
