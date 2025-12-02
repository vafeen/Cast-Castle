package ru.vafeen.castcastle.processor.processing.models

import com.google.devtools.ksp.symbol.KSFile
import ru.vafeen.castcastle.processor.processing.ProcessingVisibility

internal data class ImplMapperClass(
    val name: String,
    val packageName: String,
    val parent: KSFile?,
    val parentInterfaceName: String,
    val visibility: ProcessingVisibility,
    val implMethods: List<ImplMapperMethod>,
    val isJava: Boolean,
)