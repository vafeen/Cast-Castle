package ru.vafeen.castcastle.processor.processing

import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSValueParameter
import ru.vafeen.castcastle.annotations.CastCastleMapper
import ru.vafeen.castcastle.processor.processing.models.ClassModel
import ru.vafeen.castcastle.processor.processing.models.MapperClass
import ru.vafeen.castcastle.processor.processing.models.MapperEntity
import ru.vafeen.castcastle.processor.processing.models.MapperMethod
import ru.vafeen.castcastle.processor.processing.models.Parameter

internal interface ComponentsResolver {
    fun collectAnnotated()
    fun getMapperInterfaces(): List<MapperClass>

    companion object {
        fun create(
            resolver: Resolver,
        ): ComponentsResolver = ComponentsResolverImpl(resolver)
    }
}


internal class ComponentsResolverImpl(
    private val resolver: Resolver,
) : ComponentsResolver {
    private val annotatedInterfaces = mutableListOf<MapperClass>()

    override fun collectAnnotated() {
        getAllAnnotated().forEach {
            when {
                it is KSClassDeclaration && it.classKind == ClassKind.INTERFACE -> {
                    annotatedInterfaces.add(it.toMapperClass())
                }
            }
        }
    }

    override fun getMapperInterfaces(): List<MapperClass> = annotatedInterfaces

    private fun getAllAnnotated(): List<KSAnnotated> = resolver
        .getSymbolsWithAnnotation(CastCastleMapper::class.qualifiedName.toString())
        .toList()

    private fun KSClassDeclaration.toMapperClass(): MapperClass = MapperClass(
        name = this.simpleName.asString(),
        packageName = this.packageName.asString(),
        thisClass = this.containingFile,
        visibility = ProcessingVisibility.getClassAccessModifier(this),
        mappers = getAllMappers()
    )

    private fun KSClassDeclaration.getAllMappers(): List<MapperMethod> {
        return this.getDeclaredFunctions()
            .toList()
            .filter { it.isValidMapper() }
            .map { it.toMapperMethod() }
    }

    private fun KSClassDeclaration.toMapperEntity(): MapperEntity = MapperEntity(
        packageName = packageName.asString(),
        name = simpleName.asString(),
        classModel = this.toClassModel(),
        parameters = getParameters()
    )

    private fun KSClassDeclaration.toClassModel(): ClassModel {
        return ClassModel(
            name = simpleName.asString(),
            packageName = packageName.asString(),
            thisClass = containingFile,
            visibility = ProcessingVisibility.getClassAccessModifier(this),
            parameters = getParameters()
        )
    }

    private fun KSClassDeclaration.getParameters(): List<Parameter> =
        primaryConstructor?.parameters?.map { it.toParameter() } ?: emptyList()

    private fun KSFunctionDeclaration.toMapperMethod(): MapperMethod {
        val returnType = this.returnType?.resolve()
            ?: throw IllegalStateException("Mapper method must have return type")

        val returnClassDeclaration = returnType.declaration as? KSClassDeclaration
            ?: throw IllegalStateException("Return type must be a class declaration")

        return MapperMethod(
            from = this.parameters.first().toParameter(),
            to = returnClassDeclaration.toMapperEntity(),
            name = this.simpleName.asString(),
            isAbstract = this.isAbstract
        )
    }

    private fun KSValueParameter.toParameter(): Parameter {
        val typeModel = this.type.resolve().toClassModel()

        return Parameter(
            name = this.name?.asString() ?: "unknown",
            classModel = typeModel,
            hasDefault = this.hasDefault,
//            isVararg = this.isVararg,
        )
    }

    private fun KSType.toClassModel(): ClassModel {
        val classDeclaration = this.declaration as? KSClassDeclaration
            ?: throw IllegalArgumentException("KSType must represent a class declaration")

        return ClassModel(
            name = classDeclaration.simpleName.asString(),
            packageName = classDeclaration.packageName.asString(),
            thisClass = classDeclaration.containingFile,
            visibility = ProcessingVisibility.getClassAccessModifier(classDeclaration),
            parameters = classDeclaration.getParameters(),
        )
    }

    private fun KSValueParameter.toClassModel(): ClassModel? {
        val type = this.type.resolve()
        val classDeclaration = type.declaration as? KSClassDeclaration ?: return null
        return ClassModel(
            name = classDeclaration.simpleName.asString(),
            packageName = classDeclaration.packageName.asString(),
            thisClass = classDeclaration.containingFile!!,
            visibility = ProcessingVisibility.getClassAccessModifier(classDeclaration),
            parameters = classDeclaration.getParameters()
        )
    }


    private fun KSFunctionDeclaration.isValidMapper(): Boolean {
        return this.parameters.size == 1 &&
                this.returnType != null
    }
}