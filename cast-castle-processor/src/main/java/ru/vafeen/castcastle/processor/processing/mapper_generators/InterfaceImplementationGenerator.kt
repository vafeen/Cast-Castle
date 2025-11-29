package ru.vafeen.castcastle.processor.processing.mapper_generators

import ru.vafeen.castcastle.processor.libName
import ru.vafeen.castcastle.processor.processing.models.ImplMapperClass
import ru.vafeen.castcastle.processor.processing.models.ImplMapperMethod
import ru.vafeen.castcastle.processor.processing.models.MapperClass

internal interface InterfaceImplementationGenerator {
    fun process(mapperClass: MapperClass): ImplMapperClass

    companion object {
        fun create(): InterfaceImplementationGenerator = InterfaceImplementationGeneratorImpl()
    }
}

internal class InterfaceImplementationGeneratorImpl : InterfaceImplementationGenerator {
    override fun process(mapperClass: MapperClass): ImplMapperClass {
        return ImplMapperClass(
            name = "${mapperClass.name}$libName",
            packageName = mapperClass.packageName,
            parent = mapperClass.thisClass,
            parentInterfaceName = mapperClass.name,
            visibility = mapperClass.visibility,
            implMethods = mapperClass.mappers.map { ImplMapperMethod(it.name, it.from, it.to) }
        )
    }
}