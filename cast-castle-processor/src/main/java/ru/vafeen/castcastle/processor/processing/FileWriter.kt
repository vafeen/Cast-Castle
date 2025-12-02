package ru.vafeen.castcastle.processor.processing

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import ru.vafeen.castcastle.processor.processing.models.ImplMapperClass


internal class FileWriter(private val codeGenerator: CodeGenerator) {
    fun writeClass(
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
        )
            .writer()
            .use { out ->
                out.write(classView())
            }
    }

}
