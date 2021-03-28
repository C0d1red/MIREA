package com.c0d1red.ate;

public class Caller {
    private final Thread callThread;

    public Caller(ATE ate, String callerName) {
        Thread thread = new CallThread(ate);
        thread.setName(callerName);
        this.callThread = thread;
    }

    public void startCall() {
        callThread.start();
    }

    private static class CallThread extends Thread {
        private final ATE ate;

        public CallThread(ATE ate) {
            this.ate = ate;
        }

        @Override
        public void run() {
            ate.doCall();
        }
    }
}
