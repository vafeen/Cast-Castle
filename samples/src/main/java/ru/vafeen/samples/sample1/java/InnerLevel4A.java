package ru.vafeen.samples.sample1.java;

public class InnerLevel4A {
    private final InnerLevel5A inner1Level5;
    private final InnerLevel5A inner2Level5;

    public InnerLevel4A(InnerLevel5A inner1Level5, InnerLevel5A inner2Level5) {
        this.inner1Level5 = inner1Level5;
        this.inner2Level5 = inner2Level5;
    }

    public InnerLevel5A getInner1Level5() {
        return inner1Level5;
    }

    public InnerLevel5A getInner2Level5() {
        return inner2Level5;
    }
}
