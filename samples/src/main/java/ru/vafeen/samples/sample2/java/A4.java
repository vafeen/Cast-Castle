package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A4 {
    private final List<A5> inner1Level5;
    private final A5 inner2Level5;

    public A4(List<A5> inner1Level5, A5 inner2Level5) {
        this.inner1Level5 = inner1Level5;
        this.inner2Level5 = inner2Level5;
    }

    public List<A5> getInner1Level5() {
        return inner1Level5;
    }

    public A5 getInner2Level5() {
        return inner2Level5;
    }
}