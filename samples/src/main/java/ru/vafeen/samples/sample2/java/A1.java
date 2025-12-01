package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A1 {
    private final List<A2> inner1Level2;
    private final A2 inner2Level2;

    public A1(List<A2> inner1Level2, A2 inner2Level2) {
        this.inner1Level2 = inner1Level2;
        this.inner2Level2 = inner2Level2;
    }

    public List<A2> getInner1Level2() {
        return inner1Level2;
    }

    public A2 getInner2Level2() {
        return inner2Level2;
    }

}