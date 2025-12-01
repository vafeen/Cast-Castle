package ru.vafeen.samples.sample2.kotlin

import ru.vafeen.castcastle.annotations.CastCastleMapper

data class A(val inner1Level1: List<A1>, val inner2Level1: A1)

data class B(val inner1Level1: List<B1>, val inner2Level1: B1)

data class A1(val inner1Level2: List<A2>, val inner2Level2: A2)

data class A2(val inner1Level3: List<A3>, val inner2Level3: A3)

data class A3(val inner1Level4: List<A4>, val inner2Level4: A4)

data class A4(val inner1Level5: List<A5>, val inner2Level5: A5)

data class A5(val x: Int, val y: String)

data class B1(val inner1Level2: List<B2>, val inner2Level2: B2)

data class B2(val inner1Level3: List<B3>, val inner2Level3: B3)

data class B3(val inner1Level4: List<B4>, val inner2Level4: B4)

data class B4(val inner1Level5: List<B5>, val inner2Level5: B5)

data class B5(val x: Int, val y: String)

@CastCastleMapper
internal interface CollectionsMapper {
    fun map(a: A): B
    fun map(b: B): A

    fun string(string: String): Int = string.toInt()
    fun int(int: Int): String = "$int"
}