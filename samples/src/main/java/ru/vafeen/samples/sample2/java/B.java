package ru.vafeen.samples.sample2.java;

import java.util.List;

public class B {
    private final List<B1> inner1Level1;
    private final B1 inner2Level1;

    public B(List<B1> inner1Level1, B1 inner2Level1) {
        this.inner1Level1 = inner1Level1;
        this.inner2Level1 = inner2Level1;
    }

    public List<B1> getInner1Level1() {
        return inner1Level1;
    }

    public B1 getInner2Level1() {
        return inner2Level1;
    }
}
