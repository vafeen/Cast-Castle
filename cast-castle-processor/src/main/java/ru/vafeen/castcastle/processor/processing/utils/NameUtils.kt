package ru.vafeen.castcastle.processor.processing.utils

import ru.vafeen.castcastle.processor.processing.models.ClassModel

internal fun ClassModel.fullName() = "$packageName.$name"

internal fun ClassModel.fullNameWithGenerics(): String {
    return if (typeArguments.isNotEmpty()) {
        val genericParams = typeArguments.joinToString(", ") { it.fullName() }
        "$packageName.$name<$genericParams>"
    } else {
        fullName()
    }
}