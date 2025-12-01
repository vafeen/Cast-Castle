package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A2 {
    private final List<A3> inner1Level3;
    private final A3 inner2Level3;

    public A2(List<A3> inner1Level3, A3 inner2Level3) {
        this.inner1Level3 = inner1Level3;
        this.inner2Level3 = inner2Level3;
    }

    public List<A3> getInner1Level3() {
        return inner1Level3;
    }

    public A3 getInner2Level3() {
        return inner2Level3;
    }
}