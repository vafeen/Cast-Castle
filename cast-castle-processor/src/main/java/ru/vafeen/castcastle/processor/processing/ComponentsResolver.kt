package ru.vafeen.castcastle.processor.processing

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import ru.vafeen.castcastle.annotations.CastCastleMapper
import ru.vafeen.castcastle.processor.processing.models.ClassModel

internal interface ComponentsResolver {
    fun collectAnnotated()
    fun getMapperInterfaces(): List<ClassModel>

    companion object {
        fun create(
            resolver: Resolver,
        ): ComponentsResolver = ComponentsResolverImpl(resolver)
    }
}


internal class ComponentsResolverImpl(
    private val resolver: Resolver,
) : ComponentsResolver {
    private val annotatedInterfaces = mutableListOf<ClassModel>()

    override fun collectAnnotated() {
        getAllAnnotated().forEach {
            when {
                it is KSClassDeclaration && it.classKind == ClassKind.INTERFACE -> {
                    annotatedInterfaces.add(it.toClassModel())
                }
            }
        }
    }

    override fun getMapperInterfaces(): List<ClassModel> = annotatedInterfaces

    private fun getAllAnnotated(): List<KSAnnotated> = resolver
        .getSymbolsWithAnnotation(CastCastleMapper::class.qualifiedName.toString())
        .toList()

    private fun KSClassDeclaration.toClassModel(): ClassModel = ClassModel(
        name = this.simpleName.asString(),
        packageName = this.packageName.asString(),
        thisClass = this.containingFile!!,
        visibility = ProcessingVisibility.getClassAccessModifier(this)
    )


}