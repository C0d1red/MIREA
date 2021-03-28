package com.c0d1red;

import com.c0d1red.ate.ATE;
import com.c0d1red.ate.Caller;

public class Main {

    public static void main(String[] args) {
        testAte();
    }

    private static void testAte() {
        ATE ate = new ATE();

        Caller callerJack = new Caller(ate, "Jack");
        Caller callerBob = new Caller(ate, "Bob");
        Caller callerRoman = new Caller(ate, "Roman");

        callerJack.startCall();
        callerBob.startCall();
        callerRoman.startCall();
    }
}
