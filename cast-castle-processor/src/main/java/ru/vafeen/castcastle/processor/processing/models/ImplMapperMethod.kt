package ru.vafeen.castcastle.processor.processing.models

import ru.vafeen.castcastle.processor.processing.utils.fullName

internal class ImplMapperMethod(
    val name: String,
    val from: Parameter,
    val to: MapperEntity,
) : Writable {
    override fun asString(): String = buildString {
        appendLine("override fun $name(${from.name}: ${from.classModel.fullName()}): ${to.fullName()} {")
        appendLine("\treturn TODO()")
        appendLine("}")
    }

}
