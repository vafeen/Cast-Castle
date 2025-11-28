package ru.vafeen.castcastle.processor.processing.mapper_generators

import ru.vafeen.castcastle.processor.libName
import ru.vafeen.castcastle.processor.processing.models.ClassModel
import ru.vafeen.castcastle.processor.processing.models.ImplClassModel

internal interface InterfaceImplementationGenerator {
    fun process(classModel: ClassModel): ImplClassModel

    companion object {
        fun create(): InterfaceImplementationGenerator = InterfaceImplementationGeneratorImpl()
    }
}

internal class InterfaceImplementationGeneratorImpl : InterfaceImplementationGenerator {
    override fun process(classModel: ClassModel): ImplClassModel {
        return ImplClassModel(
            name = "${classModel.name}$libName",
            packageName = classModel.packageName,
            parent = classModel.thisClass,
            parentInterfaceName = classModel.name,
            visibility = classModel.visibility
        )
    }
}