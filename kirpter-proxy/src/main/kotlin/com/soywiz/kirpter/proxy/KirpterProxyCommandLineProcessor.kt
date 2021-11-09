package com.soywiz.kirpter.proxy

import com.soywiz.kirpter.api.*
import org.jetbrains.kotlin.cli.common.*
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*
import java.io.*

class KirpterProxyCommandLineProcessor : CommandLineProcessor {
	override val pluginId: String get() = BuildConfig.KOTLIN_PLUGIN_ID
	override val pluginOptions: Collection<AbstractCliOption> = KirpterCliOption.values().asList()

	override fun processOption(option: AbstractCliOption, value: String, configuration: CompilerConfiguration) {
		val config = configuration[KirpterConfig.KEY] ?: KirpterConfig().also { configuration.put(KirpterConfig.KEY, it) }

		//val messageCollector = configuration[CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE]
		//messageCollector!!.report(CompilerMessageSeverity.ERROR, "KirpterProxyCommandLineProcessor.processOption: $option, $value")

		when (option) {
			KirpterCliOption.TARGET_NAME -> {
				config.targetName = value
			}
			KirpterCliOption.CLASSPATH -> {
				config.processingClasspath += value.split(File.pathSeparator).map {
					File(it)
				}
			}
		}
	}
}

enum class KirpterCliOption(
	override val optionName: String,
	override val valueDescription: String,
	override val description: String,
	override val required: Boolean = false,
	override val allowMultipleOccurrences: Boolean = false
) : AbstractCliOption {
	CLASSPATH(
		"apclasspath",
		"<classpath>",
		"processor classpath",
		true
	),
	TARGET_NAME(
		"targetName",
		"<targetName>",
		"targetName",
		true
	),
	PLATFORM_TYPE(
		"platformType",
		"<platformType>",
		"platformType",
		true
	),
}