package ru.vafeen.castcastle.processor.processing.mapper_generators

import ru.vafeen.castcastle.processor.logger
import ru.vafeen.castcastle.processor.processing.models.ClassModel
import ru.vafeen.castcastle.processor.processing.models.ImplMapperClass
import ru.vafeen.castcastle.processor.processing.models.ImplMapperMethod
import ru.vafeen.castcastle.processor.processing.models.MapperMethod
import ru.vafeen.castcastle.processor.processing.models.Parameter
import ru.vafeen.castcastle.processor.processing.utils.fullName
import ru.vafeen.castcastle.processor.processing.utils.fullNameWithGenerics
import ru.vafeen.castcastle.processor.processing.utils.getCollectionElementType
import ru.vafeen.castcastle.processor.processing.utils.isCollectionType
import java.time.LocalDateTime

internal class StringViewGenerator(private val mappers: List<MapperMethod>) {
    private var isClassGenerationCalled = false

    fun generateImplMethod(implMapperMethod: ImplMapperMethod, isJava: Boolean): String {
        return buildString {
            val returnTypeName = if (implMapperMethod.to.isCollectionType()) {
                implMapperMethod.to.fullNameWithGenerics()
            } else {
                implMapperMethod.to.fullName()
            }

            appendLine(
                "override fun ${implMapperMethod.name}" +
                        "(${implMapperMethod.from.name}: ${implMapperMethod.from.classModel.fullNameWithGenerics()})" +
                        ": $returnTypeName {"
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
        val typeKey = "${sourceModel.fullNameWithGenerics()}->${targetModel.fullNameWithGenerics()}"
        if (typeKey in visitedTypes) {
            return "TODO(\"Circular mapping detected: ${sourceModel.fullNameWithGenerics()} -> ${targetModel.fullNameWithGenerics()}\")"
        }
        visitedTypes.add(typeKey)

        if (sourceModel.isCollectionType() && targetModel.isCollectionType()) {
            return generateCollectionToCollectionMapping(
                sourceVar = sourceVar,
                sourceModel = sourceModel,
                targetModel = targetModel,
                visitedTypes = visitedTypes,
                currentMapperMethod = currentMapperMethod,
                isJava = isJava
            )
        }

        val directMapper = findDirectMapper(sourceModel, targetModel, currentMapperMethod)
        return if (directMapper != null) {
            "${directMapper.name}($sourceVar)"
        } else buildString {
            // Generate constructor call\
            val targetTypeName = if (targetModel.typeArguments.isNotEmpty()) {
                targetModel.fullNameWithGenerics()
            } else {
                targetModel.fullName()
            }

            appendLine("$targetTypeName(")
            targetModel.parameters.forEach { targetParam ->
                val sourceParam = findMatchingSourceParameter(targetParam, sourceModel)

                val paramCall = if (sourceParam != null) {
                    val sourceFieldAccess = "$sourceVar.${sourceParam.name}"

                    // Check if we need collection mapping
                    if (sourceParam.classModel.isCollectionType() && targetParam.classModel.isCollectionType()) {
                        generateCollectionMapping(
                            sourceFieldAccess = sourceFieldAccess,
                            sourceElementType = sourceParam.classModel.getCollectionElementType(),
                            targetElementType = targetParam.classModel.getCollectionElementType(),
                            targetCollectionType = targetParam.classModel.fullName(),
                            targetCollectionFullType = targetParam.classModel.fullNameWithGenerics(),
                            visitedTypes = visitedTypes,
                            currentMapperMethod = currentMapperMethod,
                            isJava = isJava
                        )
                    } else if (sourceParam.classModel.fullNameWithGenerics() == targetParam.classModel.fullNameWithGenerics()) {
                        // Direct assignment for same types
                        sourceFieldAccess
                    } else {
                        // Recursive mapping for different types
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

    private fun generateCollectionToCollectionMapping(
        sourceVar: String,
        sourceModel: ClassModel,
        targetModel: ClassModel,
        visitedTypes: MutableSet<String>,
        currentMapperMethod: ImplMapperMethod,
        isJava: Boolean
    ): String {
        val sourceElementType = sourceModel.getCollectionElementType()
        val targetElementType = targetModel.getCollectionElementType()

        if (sourceElementType == null || targetElementType == null) {
            logger?.warn(
                "Cannot determine collection element types for ${sourceModel.fullNameWithGenerics()} -> ${targetModel.fullNameWithGenerics()}",
                currentMapperMethod.baseMethod
            )
            return "$sourceVar // TODO: Add explicit mapper for collection types"
        }

        // Find a mapper for the element types
        val elementMapper =
            findDirectMapper(sourceElementType, targetElementType, currentMapperMethod)

        return buildString {
            val targetCollectionType = targetModel.fullName()
            val targetCollectionFullType = targetModel.fullNameWithGenerics()
            val collectionInitializer =
                getCollectionInitializer(targetCollectionType, targetCollectionFullType)
            val receiver = getReceiver()

            append("$collectionInitializer.apply {\n")
//            append("    for (item in $sourceVar) {\n")
            append("    $sourceVar.forEach { $receiver -> \n")
            append("        ")

            if (elementMapper != null) {
                // Use existing mapper for elements
                append("add(${elementMapper.name}($receiver))")
            } else if (sourceElementType.fullNameWithGenerics() == targetElementType.fullNameWithGenerics()) {
                // Direct assignment for same element types
                append("add($receiver)")
            } else {
                // Complex mapping needed
                val mappedItem = recursiveGenerateMapperCall(
                    sourceVar = receiver,
                    sourceModel = sourceElementType,
                    targetModel = targetElementType,
                    visitedTypes = visitedTypes.toMutableSet(),
                    currentMapperMethod = currentMapperMethod,
                    isJava = isJava
                )
                append("add($mappedItem)")
            }

            append("\n    }\n")
            append("}")
        }
    }

    private var counter = 0
    private fun getReceiver(): String {
//        val parts = this.split(".")
//        val firstPart = parts.firstOrNull()?.lowercase()
//
//        val otherParts = parts.drop(1).joinToString("") { part ->
//            part.replaceFirstChar {
//                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
//            }
//        }
//        return "${firstPart}${otherParts}Receiver"
        return "it${counter++}"
    }

    private fun generateCollectionMapping(
        sourceFieldAccess: String,
        sourceElementType: ClassModel?,
        targetElementType: ClassModel?,
        targetCollectionType: String,
        targetCollectionFullType: String,
        visitedTypes: MutableSet<String>,
        currentMapperMethod: ImplMapperMethod,
        isJava: Boolean
    ): String {
        if (sourceElementType == null || targetElementType == null) {
            return "$sourceFieldAccess // TODO: Cannot determine collection element types"
        }

        // Find a mapper for the element types
        val elementMapper =
            findDirectMapper(sourceElementType, targetElementType, currentMapperMethod)

        return buildString {
            val receiver = getReceiver()
            val collectionInitializer =
                getCollectionInitializer(targetCollectionType, targetCollectionFullType)

            append("$collectionInitializer.apply {\n")
//            append("    for (item in $sourceFieldAccess) {\n")
            append("    $sourceFieldAccess.forEach { $receiver -> \n")
            append("        ")

            if (elementMapper != null) {
                // Use existing mapper for elements
//                append("add(${elementMapper.name}(item))")
                append("add(${elementMapper.name}($receiver))")
            } else if (sourceElementType.fullNameWithGenerics() == targetElementType.fullNameWithGenerics()) {
                // Direct assignment for same element types
//                append("add(item)")
                append("add($receiver)")
            } else {
                // Complex mapping needed
                val mappedItem = recursiveGenerateMapperCall(
                    sourceVar = receiver,
                    sourceModel = sourceElementType,
                    targetModel = targetElementType,
                    visitedTypes = visitedTypes.toMutableSet(),
                    currentMapperMethod = currentMapperMethod,
                    isJava = isJava
                )
                append("add($mappedItem)")
            }

            append("\n    }\n")
            append("}")
        }
    }

    private fun getCollectionInitializer(collectionType: String, fullType: String): String {
        val elementType = extractElementTypeFromCollection(fullType)

        return when {
            collectionType.contains(
                "MutableList",
                ignoreCase = true
            ) -> "mutableListOf<$elementType>()"

            collectionType.contains("ArrayList", ignoreCase = true) -> "arrayListOf<$elementType>()"
            collectionType.contains("List", ignoreCase = true) -> "mutableListOf<$elementType>()"
            collectionType.contains(
                "MutableSet",
                ignoreCase = true
            ) -> "mutableSetOf<$elementType>()"

            collectionType.contains("HashSet", ignoreCase = true) -> "hashSetOf<$elementType>()"
            collectionType.contains(
                "LinkedHashSet",
                ignoreCase = true
            ) -> "linkedSetOf<$elementType>()"

            collectionType.contains("Set", ignoreCase = true) -> "mutableSetOf<$elementType>()"
            else -> "mutableListOf<$elementType>()" // fallback
        }
    }

    private fun extractElementTypeFromCollection(fullType: String): String {
        // Извлекаем тип элемента из типа коллекции, например:
        // List<InnerLevel1A> -> InnerLevel1A
        // MutableSet<String> -> String
        val regex = "<([^>]+)>".toRegex()
        val match = regex.find(fullType)
        return match?.groupValues?.get(1) ?: "Any"
    }

    private fun findDirectMapper(
        sourceModel: ClassModel,
        targetModel: ClassModel,
        currentMapperMethod: ImplMapperMethod
    ): MapperMethod? {
        return mappers.firstOrNull { mapper ->
            // Сравниваем с учетом generic параметров
            val sourceMatches = mapper.sourceParameter.classModel.fullNameWithGenerics() ==
                    sourceModel.fullNameWithGenerics()
            val targetMatches = mapper.targetClass.fullNameWithGenerics() ==
                    targetModel.fullNameWithGenerics()

            sourceMatches && targetMatches && mapper.name != currentMapperMethod.name
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

    fun generateImplMapperClass(implMapperClass: ImplMapperClass): String {
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

    private fun String.addIndent(): String = this.prependIndent("    ")
}