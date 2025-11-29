package ru.vafeen.samples.sample1Java;

public class B {
    private final InnerLevel1B inner1Level1;
    private final InnerLevel1B inner2Level1;

    public B(InnerLevel1B inner1Level1, InnerLevel1B inner2Level1) {
        this.inner1Level1 = inner1Level1;
        this.inner2Level1 = inner2Level1;
    }

    public InnerLevel1B getInner1Level1() {
        return inner1Level1;
    }

    public InnerLevel1B getInner2Level1() {
        return inner2Level1;
    }
}
