package ru.vafeen.samples.sample2.kotlin

import ru.vafeen.castcastle.annotations.CastCastleMapper

data class A1(val x: List<A2>)
data class A2(val y: List<Int>)
data class B1(val x: List<B2>)
data class B2(val y: List<String>)

@CastCastleMapper
internal interface CollectionsMapper {
    fun map(a1: A1): B1
    fun map(b1: B1): A1

    fun string(string: String): Int = string.toInt()
    fun int(int: Int): String = "$int"
}