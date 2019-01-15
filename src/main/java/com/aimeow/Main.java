package com.aimeow;

public class Main {
    public static void main(String args[]) {
        //aync handle nums.
        new RxJavaDemos().rxtest1().delay(1000L).printAllThreads();

    }
}
