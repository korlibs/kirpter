package com.soywiz.kirpter.proxy

import com.soywiz.kirpter.api.*
import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.cli.common.*
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.com.intellij.mock.*
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*
import java.util.*

class KirpterProxyComponentRegistrar : ComponentRegistrar {
	val plugins by lazy { ServiceLoader.load(KirpterPlugin::class.java).toList() }

	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val messageCollector = configuration.get(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, MessageCollector.NONE)
		val context = KirpterContext(messageCollector)

		for (plugin in plugins) {
			plugin.createGeneratorExtension(context)?.let { IrGenerationExtension.registerExtension(project, it) }
		}
	}
}