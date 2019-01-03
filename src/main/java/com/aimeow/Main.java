package com.aimeow;

public class Main {
    public static void main(String args[]) {
        new RxJavaTest().rxtest1().delay(1000L).printAllThreads();
    }
}
