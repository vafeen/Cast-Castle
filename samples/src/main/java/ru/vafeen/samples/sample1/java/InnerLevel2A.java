package ru.vafeen.samples.sample1.java;

public class InnerLevel2A {
    private final InnerLevel3A inner1Level3;
    private final InnerLevel3A inner2Level3;

    public InnerLevel2A(InnerLevel3A inner1Level3, InnerLevel3A inner2Level3) {
        this.inner1Level3 = inner1Level3;
        this.inner2Level3 = inner2Level3;
    }

    public InnerLevel3A getInner1Level3() {
        return inner1Level3;
    }

    public InnerLevel3A getInner2Level3() {
        return inner2Level3;
    }
}
