package ru.vafeen.castcastle.processor.processing

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import ru.vafeen.castcastle.processor.processing.models.ImplClassModel

internal interface FileWriter {

    fun writeClass(implClassModel: ImplClassModel)

    companion object {
        fun create(codeGenerator: CodeGenerator): FileWriter = FileWriterImpl(codeGenerator)
    }
}

internal class FileWriterImpl(private val codeGenerator: CodeGenerator) : FileWriter {
    override fun writeClass(implClassModel: ImplClassModel) {
        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(
                aggregating = false,
                sources = arrayOf(implClassModel.parent)
            ),
            packageName = implClassModel.packageName,
            fileName = implClassModel.name
        ).writer()

        file.use { out ->
            out.write("package ${implClassModel.packageName}\n\n")

            out.write(
                "${implClassModel.visibility.nameForFile()} class ${implClassModel.name} " +
                        ": ${implClassModel.parentInterfaceName} {\n"
            )


            out.write("\n}")
        }
    }

}
