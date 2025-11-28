package ru.vafeen.castcastle.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

class CastCastleProcessorProvider : SymbolProcessorProvider {
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {

        return CastCastleProcessor(
            codeGenerator = environment.codeGenerator,
            kspLogger = environment.logger
        )
    }
}