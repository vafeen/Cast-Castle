package ru.vafeen.samples.sample1.java;

public class InnerLevel1A {
    private final InnerLevel2A inner1Level2;
    private final InnerLevel2A inner2Level2;

    public InnerLevel1A(InnerLevel2A inner1Level2, InnerLevel2A inner2Level2) {
        this.inner1Level2 = inner1Level2;
        this.inner2Level2 = inner2Level2;
    }

    public InnerLevel2A getInner1Level2() {
        return inner1Level2;
    }

    public InnerLevel2A getInner2Level2() {
        return inner2Level2;
    }
}
