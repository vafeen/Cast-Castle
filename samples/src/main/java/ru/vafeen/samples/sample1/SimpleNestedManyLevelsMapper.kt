package ru.vafeen.samples.sample1

import ru.vafeen.castcastle.annotations.CastCastleMapper

data class A(val inner1Level1: InnerLevel1A, val inner2Level1: InnerLevel1A)

data class B(val inner1Level1: InnerLevel1B, val inner2Level1: InnerLevel1B)

data class InnerLevel1A(val inner1Level2: InnerLevel2A, val inner2Level2: InnerLevel2A)

data class InnerLevel2A(val inner1Level3: InnerLevel3A, val inner2Level3: InnerLevel3A)

data class InnerLevel3A(val inner1Level4: InnerLevel4A, val inner2Level4: InnerLevel4A)

data class InnerLevel4A(val inner1Level5: InnerLevel5A, val inner2Level5: InnerLevel5A)

data class InnerLevel5A(val x: Int, val y: String)

data class InnerLevel1B(
    val inner1Level2: InnerLevel2B,
    val inner2Level2: InnerLevel2B,
)

data class InnerLevel2B(val inner1Level3: InnerLevel3B, val inner2Level3: InnerLevel3B)

data class InnerLevel3B(val inner1Level4: InnerLevel4B, val inner2Level4: InnerLevel4B)

data class InnerLevel4B(val inner1Level5: InnerLevel5B, val inner2Level5: InnerLevel5B)

data class InnerLevel5B(val x: Int, val y: String)

@CastCastleMapper
interface SimpleNestedManyLevelsMapper {
    fun map(a: A): B
    fun map(b: B): A
}