package com.baeldung.lnj.domain.model;

public class MyResource implements AutoCloseable {
    public MyResource() {
        System.out.println("Resource opened");
    }

    public void close() {
        System.out.println("Resource closed");
    }
}

