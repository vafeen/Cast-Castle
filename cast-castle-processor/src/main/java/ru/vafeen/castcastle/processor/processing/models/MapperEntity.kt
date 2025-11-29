package ru.vafeen.castcastle.processor.processing.models

internal data class MapperEntity(
    val packageName: String,
    val name: String,
    val classModel: ClassModel,
    val parameters: List<Parameter>
)