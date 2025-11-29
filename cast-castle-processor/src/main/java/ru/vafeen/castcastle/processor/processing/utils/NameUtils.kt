package ru.vafeen.castcastle.processor.processing.utils

import ru.vafeen.castcastle.processor.processing.models.ClassModel

internal fun ClassModel.fullName() = "$packageName.$name"
