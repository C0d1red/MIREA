package com.c0d1red;

public class Main {
    private static final String CLIENT_USERNAME = "C0d1red";
    private static final String CLIENT_PASSWORD = "123456";

    public static void main(String[] args) {
        TestScript testScript = new TestScript(CLIENT_USERNAME, CLIENT_PASSWORD);
        testScript.register();
        testScript.authenticate();
        testScript.verify();
    }

}
