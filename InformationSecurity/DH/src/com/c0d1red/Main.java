package com.c0d1red;

import com.c0d1red.dh.DH;

public class Main {

    public static void main(String[] args) {
        DH dh = new DH();
        Person bob = new Person(dh);
        Person jack = new Person(dh);

        int p = bob.createP();
        int g = bob.createG();
        System.out.println("p: " + p);
        System.out.println("g: " + g);
        System.out.println();

        bob.createPrivateKey();
        jack.createPrivateKey();
        System.out.print("Bob: ");
        bob.sayPrivateKey();
        System.out.print("Jack: ");
        jack.sayPrivateKey();
        System.out.println();

        int bobPublicKey = bob.createPublicKey();
        int jackPublicKey = jack.createPublicKey();
        System.out.println("Bob's public key: " + bobPublicKey);
        System.out.println("Jack's public key: " + jackPublicKey);
        System.out.println();

        bob.createGeneralSecretKey(jackPublicKey);
        jack.createGeneralSecretKey(bobPublicKey);
        System.out.print("Bob: ");
        bob.sayGeneralSecretKey();
        System.out.print("Jack: ");
        jack.sayGeneralSecretKey();
    }

}
