package ru.vafeen.samples.sample2.java;

import java.util.List;

public class A {
    private final List<InnerLevel1A> inner1Level1;
    private final InnerLevel1A inner2Level1;

    public A(List<InnerLevel1A> inner1Level1, InnerLevel1A inner2Level1) {
        this.inner1Level1 = inner1Level1;
        this.inner2Level1 = inner2Level1;
    }

    public List<InnerLevel1A> getInner1Level1() {
        return inner1Level1;
    }

    public InnerLevel1A getInner2Level1() {
        return inner2Level1;
    }
}