package ru.vafeen.samples.sample2.java;

import java.util.List;

public class B1 {
    private final List<B2> inner1Level2;
    private final B2 inner2Level2;

    public B1(List<B2> inner1Level2, B2 inner2Level2) {
        this.inner1Level2 = inner1Level2;
        this.inner2Level2 = inner2Level2;
    }

    public List<B2> getInner1Level2() {
        return inner1Level2;
    }

    public B2 getInner2Level2() {
        return inner2Level2;
    }
}