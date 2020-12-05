package com.c0d1red;

import com.c0d1red.dh.DH;

public class Person {
    private int privateKey;
    private int publicKey;
    private int generalSecretKey;
    private final DH dh;

    public Person(DH dh){
        this.dh = dh;
    }

    public int getPublicKey() {
        return publicKey;
    }

    public void createPrivateKey(){
        privateKey = dh.generatePrivateKey();
    }

    public int createP(){
        return dh.generateP();
    }

    public int createG(){
        return dh.generateG();
    }

    public int createPublicKey(){
        publicKey = dh.calculatePersonalPublicKey(privateKey);
        return publicKey;
    }

    public void createGeneralSecretKey(int anotherPersonPublicKey){
        generalSecretKey = dh.calculateGeneralSecretKey(anotherPersonPublicKey, privateKey);
    }

    public void sayPrivateKey(){
        System.out.println("My private key is " + privateKey);
    }

    public void sayGeneralSecretKey(){
        System.out.println("My general secret key is " + generalSecretKey);
    }

}
