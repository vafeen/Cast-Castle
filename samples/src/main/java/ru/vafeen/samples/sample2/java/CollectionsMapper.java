package ru.vafeen.samples.sample2.java;

import ru.vafeen.castcastle.annotations.CastCastleMapper;


@CastCastleMapper
interface CollectionsMapper {
    B map(A a);

    A map(B b);

    default int mapString(String string) {
        return Integer.parseInt(string);
    }

    default String mapInt(int i) {
        return String.valueOf(i);
    }
}