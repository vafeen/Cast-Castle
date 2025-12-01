package ru.vafeen.samples.sample2.kotlin

import ru.vafeen.castcastle.annotations.CastCastleMapper

data class A(val inner1Level1: List<InnerLevel1A>, val inner2Level1: InnerLevel1A)
data class B(val inner1Level1: List<InnerLevel1B>, val inner2Level1: InnerLevel1B)
data class InnerLevel1A(val inner1Level2: List<InnerLevel2A>, val inner2Level2: InnerLevel2A)
data class InnerLevel1B(val inner1Level2: List<InnerLevel2B>, val inner2Level2: InnerLevel2B)
data class InnerLevel2A(val inner1Level3: List<InnerLevel3A>, val inner2Level3: InnerLevel3A)
data class InnerLevel2B(val inner1Level3: List<InnerLevel3B>, val inner2Level3: InnerLevel3B)
data class InnerLevel3A(val inner1Level4: List<InnerLevel4A>, val inner2Level4: InnerLevel4A)
data class InnerLevel3B(val inner1Level4: List<InnerLevel4B>, val inner2Level4: InnerLevel4B)
data class InnerLevel4A(val inner1Level5: List<InnerLevel5A>, val inner2Level5: InnerLevel5A)
data class InnerLevel4B(val inner1Level5: List<InnerLevel5B>, val inner2Level5: InnerLevel5B)
data class InnerLevel5A(val x: String, val y: Int)
data class InnerLevel5B(val x: Int, val y: String)

@CastCastleMapper
internal interface CollectionsMapper {
    fun map(a: A): B
    fun map(b: B): A

    fun string(string: String): Int = string.toInt()
    fun int(int: Int): String = "$int"
    fun mapLevel1A(inner1Level1: InnerLevel1A): InnerLevel1B
    fun mapLevel1B(inner1Level1: InnerLevel1B): InnerLevel1A
    fun mapLevel2A(innerLevel2A: InnerLevel2A): InnerLevel2B
    fun mapLevel2B(innerLevel2A: InnerLevel2B): InnerLevel2A
    fun mapLevel3A(inner1Level3: InnerLevel3A): InnerLevel3B
    fun mapLevel3B(inner1Level3: InnerLevel3B): InnerLevel3A
    fun mapLevel4A(inner1Level4: InnerLevel4A): InnerLevel4B
    fun mapLevel4B(inner1Level4: InnerLevel4B): InnerLevel4A
    fun mapLevel5A(inner1Level5: InnerLevel5A): InnerLevel5B
    fun mapLevel5B(inner1Level5: InnerLevel5B): InnerLevel5A
    fun mapLevel1(inner1Level1: List<InnerLevel1A>): List<InnerLevel1B>
    fun mapLevel1(inner1Level1: List<InnerLevel1B>): List<InnerLevel1A>
    fun mapLevel2(innerLevel2A: List<InnerLevel2A>): List<InnerLevel2B>
    fun mapLevel2(innerLevel2A: List<InnerLevel2B>): List<InnerLevel2A>
    fun mapLevel3(inner1Level3: List<InnerLevel3A>): List<InnerLevel3B>
    fun mapLevel3(inner1Level3: List<InnerLevel3B>): List<InnerLevel3A>
    fun mapLevel4(inner1Level4: List<InnerLevel4A>): List<InnerLevel4B>
    fun mapLevel4(inner1Level4: List<InnerLevel4B>): List<InnerLevel4A>
    fun mapLevel5(inner1Level5: List<InnerLevel5A>): List<InnerLevel5B>
    fun mapLevel5(inner1Level5: List<InnerLevel5B>): List<InnerLevel5A>
}