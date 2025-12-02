package ru.vafeen.samples.sample2.java;

import java.util.List;

public class InnerLevel1B {
    private final List<InnerLevel2B> inner1Level2;
    private final InnerLevel2B inner2Level2;

    public InnerLevel1B(List<InnerLevel2B> inner1Level2, InnerLevel2B inner2Level2) {
        this.inner1Level2 = inner1Level2;
        this.inner2Level2 = inner2Level2;
    }

    public List<InnerLevel2B> getInner1Level2() {
        return inner1Level2;
    }

    public InnerLevel2B getInner2Level2() {
        return inner2Level2;
    }
}