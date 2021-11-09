package com.soywiz.kirpter.api

import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.cli.common.*
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.com.intellij.mock.*
import org.jetbrains.kotlin.config.*
import java.io.*

interface KirpterPlugin {
	fun createGeneratorExtension(context: KirpterContext): IrGenerationExtension? = null
}

data class KirpterContext(
	val configuration: CompilerConfiguration,
	val config: KirpterConfig,
	val project: MockProject
) {
	val messageCollector: MessageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
}

class KirpterConfig {
	companion object {
		val KEY = CompilerConfigurationKey.create<KirpterConfig>("Ksp options")
	}

	var targetName: String = "unknown"
	var processingClasspath: List<File> = emptyList()
}
