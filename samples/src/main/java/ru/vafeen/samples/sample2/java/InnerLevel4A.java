package ru.vafeen.samples.sample2.java;

import java.util.List;

public class InnerLevel4A {
    private final List<InnerLevel5A> inner1Level5;
    private final InnerLevel5A inner2Level5;

    public InnerLevel4A(List<InnerLevel5A> inner1Level5, InnerLevel5A inner2Level5) {
        this.inner1Level5 = inner1Level5;
        this.inner2Level5 = inner2Level5;
    }

    public List<InnerLevel5A> getInner1Level5() {
        return inner1Level5;
    }

    public InnerLevel5A getInner2Level5() {
        return inner2Level5;
    }
}