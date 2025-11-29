package ru.vafeen.castcastle.processor.processing.models

internal data class MapperMethod(
    val name: String,
    val from: Parameter,
    val to: MapperEntity
)