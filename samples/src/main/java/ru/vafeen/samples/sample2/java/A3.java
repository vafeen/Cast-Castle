package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A3 {
    private final List<A4> inner1Level4;
    private final A4 inner2Level4;

    public A3(List<A4> inner1Level4, A4 inner2Level4) {
        this.inner1Level4 = inner1Level4;
        this.inner2Level4 = inner2Level4;
    }

    public List<A4> getInner1Level4() {
        return inner1Level4;
    }

    public A4 getInner2Level4() {
        return inner2Level4;
    }
}