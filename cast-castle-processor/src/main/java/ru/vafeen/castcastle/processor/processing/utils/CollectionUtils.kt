package ru.vafeen.castcastle.processor.processing.utils

import ru.vafeen.castcastle.processor.processing.models.ClassModel

internal fun ClassModel.isCollectionType(): Boolean {
    val collectionTypes = listOf(
        "List", "MutableList", "Set", "MutableSet",
        "Collection", "MutableCollection", "ArrayList", "LinkedList",
        "HashSet", "LinkedHashSet", "Iterable"
    )

    return collectionTypes.contains(name) ||
            (packageName.startsWith("kotlin.collections") &&
                    (name.endsWith("List") || name.endsWith("Set") || name.endsWith("Collection"))) ||
            (packageName == "java.util" &&
                    (name.endsWith("List") || name.endsWith("Set") || name.endsWith("Collection")))
}

internal fun ClassModel.getCollectionElementType(): ClassModel? {
    if (!isCollectionType()) return null
    return typeArguments.firstOrNull()
}