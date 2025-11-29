package ru.vafeen.castcastle.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import ru.vafeen.castcastle.processor.processing.ComponentsResolver
import ru.vafeen.castcastle.processor.processing.FileWriter
import ru.vafeen.castcastle.processor.processing.mapper_generators.StringViewGenerator
import ru.vafeen.castcastle.processor.processing.utils.toImplClassModel

internal var logger: KSPLogger? = null
internal val libName = "CastCastle"

class CastCastleProcessor private constructor(codeGenerator: CodeGenerator) : SymbolProcessor {

    constructor(
        codeGenerator: CodeGenerator,
        kspLogger: KSPLogger
    ) : this(codeGenerator) {
        logger = kspLogger
    }


    private val fileWriter = FileWriter.create(codeGenerator)
    override fun process(resolver: Resolver): List<KSAnnotated> {
        logger?.info("This is ${this::class.simpleName}")
        val componentsResolver = ComponentsResolver.create(resolver)
            .also(ComponentsResolver::collectAnnotated)
        val interfaces = componentsResolver.getMapperInterfaces()

        interfaces.forEach {
            val implementation = it.toImplClassModel()
            fileWriter.writeClass(implementation) {
                val mappersForThisClass = componentsResolver.getAllMappersForThisInterface(it)
                val stringViewGenerator = StringViewGenerator.create(mappersForThisClass)
                stringViewGenerator.generateImplMapperClass(implementation)
            }
        }

        return emptyList()
    }


}