package com.c0d1red.ate;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ATE {
    private static final int SECONDS_CALL_MIN = 2;
    private static final int SECONDS_CALL_MAX = 10;
    private final Lock lock;
    private boolean isCalling;

    public ATE() {
        lock = new ReentrantLock();
        isCalling = false;
    }

    public void doCall() {
        lock.lock();

        checkExplosion();
        startConnection();
        callProcess();
        finishConnection();

        lock.unlock();
    }

    private void checkExplosion() {
        if (isCalling) {
            throw new ATEExplosionException();
        }
    }

    private void startConnection() {
        isCalling = true;
        System.out.format("%s started the call\n", Thread.currentThread().getName());
    }

    private void callProcess() {
        try {
            TimeUnit.SECONDS.sleep(getRandomCallTime());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void finishConnection() {
        isCalling = false;
        System.out.format("%s finished the call\n", Thread.currentThread().getName());
    }

    private int getRandomCallTime() {
        Random random = new Random();
        return random.nextInt(SECONDS_CALL_MAX-SECONDS_CALL_MIN) + SECONDS_CALL_MIN;
    }
}
