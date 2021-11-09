package com.soywiz.kirpter.proxy

import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*
import java.io.*

class KirpterProxyCommandLineProcessor : CommandLineProcessor {
	override val pluginId: String get() = "Kirpter"
	override val pluginOptions: Collection<AbstractCliOption> get() = KirpterCliOption.values().asList()

	override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
		val config = configuration[KirpterConfig.KEY] ?: KirpterConfig().also { configuration.put(KirpterConfig.KEY, it) }

		when (option) {
			KirpterCliOption.PROCESSOR_CLASSPATH_OPTION -> {
				config.processingClasspath += value.split(File.pathSeparator).map {
					File(it)
				}
			}
		}
	}
}

class KirpterConfig {
	companion object {
		val KEY = CompilerConfigurationKey.create<KirpterConfig>("Ksp options")
	}

	var processingClasspath: List<File> = emptyList()
}

enum class KirpterCliOption(
	override val optionName: String,
	override val valueDescription: String,
	override val description: String,
	override val required: Boolean = false,
	override val allowMultipleOccurrences: Boolean = false
) : AbstractCliOption {
	PROCESSOR_CLASSPATH_OPTION(
		"apclasspath",
		"<classpath>",
		"processor classpath",
		false
	),
}