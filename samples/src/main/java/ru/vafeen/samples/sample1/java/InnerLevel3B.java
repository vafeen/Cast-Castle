package ru.vafeen.samples.sample1.java;

public class InnerLevel3B {
    private final InnerLevel4B inner1Level4;
    private final InnerLevel4B inner2Level4;

    public InnerLevel3B(InnerLevel4B inner1Level4, InnerLevel4B inner2Level4) {
        this.inner1Level4 = inner1Level4;
        this.inner2Level4 = inner2Level4;
    }

    public InnerLevel4B getInner1Level4() {
        return inner1Level4;
    }

    public InnerLevel4B getInner2Level4() {
        return inner2Level4;
    }
}
