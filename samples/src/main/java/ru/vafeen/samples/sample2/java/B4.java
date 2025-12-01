package ru.vafeen.samples.sample2.java;

import java.util.List;

public class B4 {
    private final List<B5> inner1Level5;
    private final B5 inner2Level5;

    public B4(List<B5> inner1Level5, B5 inner2Level5) {
        this.inner1Level5 = inner1Level5;
        this.inner2Level5 = inner2Level5;
    }

    public List<B5> getInner1Level5() {
        return inner1Level5;
    }

    public B5 getInner2Level5() {
        return inner2Level5;
    }
}