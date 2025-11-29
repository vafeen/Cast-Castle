package ru.vafeen.castcastle.processor.processing.models

internal data class Parameter(
    val name: String,
    val classModel: ClassModel,
    val hasDefault: Boolean,
)