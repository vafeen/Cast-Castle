package ru.vafeen.samples.sample2.java;

import java.util.List;

public class InnerLevel2B {
    private final List<InnerLevel3B> inner1Level3;
    private final InnerLevel3B inner2Level3;

    public InnerLevel2B(List<InnerLevel3B> inner1Level3, InnerLevel3B inner2Level3) {
        this.inner1Level3 = inner1Level3;
        this.inner2Level3 = inner2Level3;
    }

    public List<InnerLevel3B> getInner1Level3() {
        return inner1Level3;
    }

    public InnerLevel3B getInner2Level3() {
        return inner2Level3;
    }
}