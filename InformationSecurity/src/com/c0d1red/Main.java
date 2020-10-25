package com.c0d1red;

import com.c0d1red.rsa.PrivateKey;
import com.c0d1red.rsa.PublicKey;
import com.c0d1red.rsa.RSA;

import java.util.Scanner;

public class Main {
    private static final String TEST_TERM = "Hello, world!";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String term = scanner.nextLine();

        RSA rsa = new RSA();
        PublicKey publicKey = rsa.createPublicKey();
        PrivateKey privateKey = rsa.createPrivateKey();

        System.out.println(publicKey);
        System.out.println(privateKey);

        String encryptedTerm = rsa.encrypt(term, publicKey);
        System.out.println("Encrypted term: " + encryptedTerm);

        String decryptedTerm = rsa.decrypt(encryptedTerm, privateKey);
        System.out.println("Decrypted term: " + decryptedTerm);
    }

}
