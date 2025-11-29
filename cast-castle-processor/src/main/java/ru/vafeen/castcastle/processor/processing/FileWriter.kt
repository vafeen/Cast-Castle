package ru.vafeen.castcastle.processor.processing

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import ru.vafeen.castcastle.processor.processing.models.ImplMapperClass

internal interface FileWriter {

    fun writeClass(implMapperClass: ImplMapperClass, classView: () -> String)

    companion object {
        fun create(codeGenerator: CodeGenerator): FileWriter = FileWriterImpl(codeGenerator)
    }
}

internal class FileWriterImpl(private val codeGenerator: CodeGenerator) : FileWriter {
    override fun writeClass(
        implMapperClass: ImplMapperClass,
        classView: () -> String
    ) {
        val parent = implMapperClass.parent
        codeGenerator.createNewFile(
            dependencies = Dependencies(
                aggregating = false,
                sources = if (parent != null) arrayOf(parent) else arrayOf()
            ),
            packageName = implMapperClass.packageName,
            fileName = implMapperClass.name
        ).writer().use { out ->
            out.write(classView())
        }
    }

}
