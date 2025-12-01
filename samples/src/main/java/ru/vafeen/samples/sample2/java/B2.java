package ru.vafeen.samples.sample2.java;

import java.util.List;

public class B2 {
    private final List<B3> inner1Level3;
    private final B3 inner2Level3;

    public B2(List<B3> inner1Level3, B3 inner2Level3) {
        this.inner1Level3 = inner1Level3;
        this.inner2Level3 = inner2Level3;
    }

    public List<B3> getInner1Level3() {
        return inner1Level3;
    }

    public B3 getInner2Level3() {
        return inner2Level3;
    }

}