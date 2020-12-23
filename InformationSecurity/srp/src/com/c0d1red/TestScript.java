package com.c0d1red;

import com.c0d1red.srp.Client;
import com.c0d1red.srp.Server;

public class TestScript {
    private final Client client;
    private final Server server;

    public TestScript(String username, String password) {
        client = new Client(username, password);
        server = new Server();
    }

    public void register() {
        System.out.println("==================REGISTRATION==================");
        server.register(client.getUsername(), client.getV());
        System.out.println("Username: " + client.getUsername());
        System.out.println("Password verifier: " + client.getV());
    }

    public void authenticate() {
        System.out.println("\n\n==================AUTHENTICATION==================");
        createA();
        createB();
        calculateU();
        calculateGeneralKeys();
    }

    public void verify() {
        System.out.println("\n\n==================VERIFICATION==================");
        calculateM();

        boolean isMEquals = client.getM().equals(server.getM());
        System.out.printf("Server's M == Client's M? {%s}%n", isMEquals);
        System.out.println();
        if (isMEquals) {
            calculateR();

            boolean isREquals = client.getR().equals(server.getR());
            System.out.printf("Server's R == Client's R? {%s}%n", isREquals);
        }
    }

    private void createA() {
        client.generateRandomNumber();
        client.calculateA();
        server.verifyA(client.getA());
    }

    private void createB() {
        server.generateRandomNumber();
        server.calculateB();
        client.verifyB(server.getB());
    }

    private void calculateU() {
        client.calculateU(server.getB());
        server.calculateU(client.getA());
    }

    private void calculateGeneralKeys() {
        client.calculateGeneralKey(server.getB());
        System.out.println("Server's general key: " + client.getGeneralKey());
        server.calculateGeneralKey(client.getA());
        System.out.println("Client's general key: " + server.getGeneralKey());

        boolean isKeysEquals = client.getGeneralKey().equals(server.getGeneralKey());
        System.out.printf("Server's general key == Client's general key? {%s}%n", isKeysEquals);
    }

    private void calculateM() {
        client.calculateM(server.getB());
        System.out.println("Client's M: " + client.getM());
        server.calculateM(client.getA());
        System.out.println("Server's M: " + server.getM());
    }

    private void calculateR() {
        server.calculateR(client.getA());
        System.out.println("Server r: " + server.getR());
        client.calculateR();
        System.out.println("Client r: " + client.getR());
    }
}
