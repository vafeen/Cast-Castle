package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A {
    private final List<A1> inner1Level1;
    private final A1 inner2Level1;

    public A(List<A1> inner1Level1, A1 inner2Level1) {
        this.inner1Level1 = inner1Level1;
        this.inner2Level1 = inner2Level1;
    }

    public List<A1> getInner1Level1() {
        return inner1Level1;
    }

    public A1 getInner2Level1() {
        return inner2Level1;
    }
}