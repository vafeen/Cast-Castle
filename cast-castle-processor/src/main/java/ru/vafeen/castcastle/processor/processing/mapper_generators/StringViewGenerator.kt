package ru.vafeen.castcastle.processor.processing.mapper_generators

import ru.vafeen.castcastle.processor.logger
import ru.vafeen.castcastle.processor.processing.models.ClassModel
import ru.vafeen.castcastle.processor.processing.models.ImplMapperClass
import ru.vafeen.castcastle.processor.processing.models.ImplMapperMethod
import ru.vafeen.castcastle.processor.processing.models.MapperMethod
import ru.vafeen.castcastle.processor.processing.models.Parameter
import ru.vafeen.castcastle.processor.processing.utils.fullName
import java.time.LocalDateTime


internal interface StringViewGenerator {
    fun generateImplMethod(implMapperMethod: ImplMapperMethod, isJava: Boolean): String
    fun generateImplMapperClass(implMapperClass: ImplMapperClass): String

    companion object {
        fun create(mappers: List<MapperMethod>): StringViewGenerator =
            StringViewGeneratorImpl(mappers)
    }
}

internal class StringViewGeneratorImpl(private val mappers: List<MapperMethod>) :
    StringViewGenerator {
    private var isClassGenerationCalled = false
    private fun String.addIndent(): String = this.prependIndent("    ")
    override fun generateImplMethod(implMapperMethod: ImplMapperMethod, isJava: Boolean): String {
        return buildString {
            appendLine(
                "override fun ${implMapperMethod.name}" +
                        "(${implMapperMethod.from.name}: ${implMapperMethod.from.classModel.fullName()})" +
                        ": ${implMapperMethod.to.fullName()} {"
            )
            appendLine(
                "return ${
                    recursiveGenerateMapperCall(
                        sourceVar = implMapperMethod.from.name,
                        sourceModel = implMapperMethod.from.classModel,
                        targetModel = implMapperMethod.to,
                        visitedTypes = mutableSetOf(),
                        currentMapperMethod = implMapperMethod,
                        isJava = isJava
                    )
                }".addIndent()
            )
            appendLine("}")

        }
    }

    private fun recursiveGenerateMapperCall(
        sourceVar: String,
        sourceModel: ClassModel,
        targetModel: ClassModel,
        visitedTypes: MutableSet<String>,
        currentMapperMethod: ImplMapperMethod,
        isJava: Boolean,
    ): String {
        val typeKey = "${sourceModel.fullName()}->${targetModel.fullName()}"
        if (typeKey in visitedTypes) {
            return "TODO(\"Circular mapping detected: ${sourceModel.fullName()} -> ${targetModel.fullName()}\")"
        }
        visitedTypes.add(typeKey)

        val directMapper = findDirectMapper(sourceModel, targetModel, currentMapperMethod)
        return if (directMapper != null) {
            "${directMapper.name}($sourceVar)"
        } else buildString {
            // if no direct mapper, generate with constructor
            appendLine("${targetModel.fullName()}(")
            targetModel.parameters.forEach { targetParam ->
                val sourceParam = findMatchingSourceParameter(targetParam, sourceModel)

                val paramCall = if (sourceParam != null) {
                    val sourceFieldAccess = "$sourceVar.${sourceParam.name}"

                    // if types the same - direct assignment
                    if (sourceParam.classModel.fullName() == targetParam.classModel.fullName()) {
                        sourceFieldAccess
                    } else {
                        recursiveGenerateMapperCall(
                            sourceVar = sourceFieldAccess,
                            sourceModel = sourceParam.classModel,
                            targetModel = targetParam.classModel,
                            visitedTypes = visitedTypes.toMutableSet(),
                            currentMapperMethod = currentMapperMethod,
                            isJava = isJava
                        )
                    }
                } else {
                    logger?.error(
                        "No value with the same name " +
//                            "and no helpers" +
                                "provided for ${targetParam.name}",
                        currentMapperMethod.baseMethod
                    )
                    // Parameter is not found
                    "TODO(\"Provide value " +
//                            "or helper"+
                            "for ${targetParam.name}\")"
                }
                appendLine(
                    "${if (!isJava) "${targetParam.name} = " else ""}$paramCall,".addIndent()
                )
            }

            append(")")
        }
    }

    // todo здесь сделать если 2 маппера задетектилось - то ошибку выдавать "типа uncertainty - неопределенность"
    private fun findDirectMapper(
        sourceModel: ClassModel,
        targetModel: ClassModel,
        currentMapperMethod: ImplMapperMethod
    ): MapperMethod? {
//        if (matchingMappers.size > 1) {
//            // Пока просто берем первый, но можно добавить логику выбора или ошибку
//            // throw IllegalStateException("Multiple mappers found for ${sourceModel.fullName()} -> ${targetModel.fullName()}")
//        }
        return mappers.firstOrNull { mapper ->
            mapper.sourceParameter.classModel.fullName() == sourceModel.fullName() &&
                    mapper.targetClass.fullName() == targetModel.fullName() &&
                    mapper.name != currentMapperMethod.name
        }
    }

    private fun findMatchingSourceParameter(
        targetParam: Parameter,
        sourceModel: ClassModel
    ): Parameter? {
        return sourceModel.parameters.find { sourceParam ->
            sourceParam.name == targetParam.name
        }
    }

    override fun generateImplMapperClass(implMapperClass: ImplMapperClass): String {
        if (isClassGenerationCalled) throw Exception("${StringViewGenerator::class.simpleName} must be called once for every implementation")
        isClassGenerationCalled = true
        return buildString {
            appendLine("package ${implMapperClass.packageName}\n")
            appendLine("//updated: ${LocalDateTime.now()}\n")
            appendLine("${implMapperClass.visibility.nameForFile()} class ${implMapperClass.name} : ${implMapperClass.parentInterfaceName} {")
            appendLine(implMapperClass.implMethods.joinToString(separator = "\n") {
                generateImplMethod(
                    implMapperMethod = it,
                    isJava = implMapperClass.isJava
                ).addIndent()
            })
            appendLine("}")
        }
    }
}
