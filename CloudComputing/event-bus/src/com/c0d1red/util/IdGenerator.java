package com.c0d1red.util;

public class IdGenerator {
    private long currentId;

    public IdGenerator() {
        currentId = 0;
    }

    public long getNextId() {
        return ++currentId;
    }
}
