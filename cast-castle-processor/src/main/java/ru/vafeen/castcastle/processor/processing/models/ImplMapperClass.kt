package ru.vafeen.castcastle.processor.processing.models

import com.google.devtools.ksp.symbol.KSFile
import ru.vafeen.castcastle.processor.processing.ProcessingVisibility
import java.time.LocalDateTime

internal data class ImplMapperClass(
    val name: String,
    val packageName: String,
    val parent: KSFile?,
    val parentInterfaceName: String,
    val visibility: ProcessingVisibility,
    val implMethods: List<ImplMapperMethod>
) : Writable {
    override fun asString(): String = buildString {
        appendLine("package $packageName")
        append("\n")
        appendLine("//updated: ${LocalDateTime.now()}\n")
        appendLine("${visibility.nameForFile()} class $name : $parentInterfaceName {")
        appendLine(implMethods.joinToString(separator = "\n") {
            it.asString().prependIndent("\t")
        })
        appendLine("}")
    }

}