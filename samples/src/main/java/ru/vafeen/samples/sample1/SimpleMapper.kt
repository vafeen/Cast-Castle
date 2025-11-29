package ru.vafeen.samples.sample1

import ru.vafeen.castcastle.annotations.CastCastleMapper

data class A(val x: Int)
data class B(val x: Int)

@CastCastleMapper
interface SimpleMapper {
    fun map(a: A): B
}