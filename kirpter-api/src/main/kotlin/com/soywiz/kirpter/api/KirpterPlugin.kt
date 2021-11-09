package com.soywiz.kirpter.api

import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.cli.common.messages.*

data class KirpterContext(
	val messageCollector: MessageCollector,
)

interface KirpterPlugin {
	fun createGeneratorExtension(context: KirpterContext): IrGenerationExtension? = null
}
