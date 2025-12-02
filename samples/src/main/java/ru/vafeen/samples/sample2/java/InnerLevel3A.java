package ru.vafeen.samples.sample2.java;

import java.util.List;

public class InnerLevel3A {
    private final List<InnerLevel4A> inner1Level4;
    private final InnerLevel4A inner2Level4;

    public InnerLevel3A(List<InnerLevel4A> inner1Level4, InnerLevel4A inner2Level4) {
        this.inner1Level4 = inner1Level4;
        this.inner2Level4 = inner2Level4;
    }

    public List<InnerLevel4A> getInner1Level4() {
        return inner1Level4;
    }

    public InnerLevel4A getInner2Level4() {
        return inner2Level4;
    }
}