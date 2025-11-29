package ru.vafeen.samples.sample1.java;

public class InnerLevel4B {
    private final InnerLevel5B inner1Level5;
    private final InnerLevel5B inner2Level5;

    public InnerLevel4B(InnerLevel5B inner1Level5, InnerLevel5B inner2Level5) {
        this.inner1Level5 = inner1Level5;
        this.inner2Level5 = inner2Level5;
    }

    public InnerLevel5B getInner1Level5() {
        return inner1Level5;
    }

    public InnerLevel5B getInner2Level5() {
        return inner2Level5;
    }
}
