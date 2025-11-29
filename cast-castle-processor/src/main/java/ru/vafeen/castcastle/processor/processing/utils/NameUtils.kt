package ru.vafeen.castcastle.processor.processing.utils

import ru.vafeen.castcastle.processor.processing.models.ClassModel
import ru.vafeen.castcastle.processor.processing.models.MapperEntity

internal fun ClassModel.fullName() = "$packageName.$name"
internal fun MapperEntity.fullName() = "$packageName.$name"
