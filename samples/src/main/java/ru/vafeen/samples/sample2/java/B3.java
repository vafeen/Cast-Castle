package ru.vafeen.samples.sample2.java;


import java.util.List;

public class B3 {
    private final List<B4> inner1Level4;
    private final B4 inner2Level4;

    public B3(List<B4> inner1Level4, B4 inner2Level4) {
        this.inner1Level4 = inner1Level4;
        this.inner2Level4 = inner2Level4;
    }

    public List<B4> getInner1Level4() {
        return inner1Level4;
    }

    public B4 getInner2Level4() {
        return inner2Level4;
    }

}