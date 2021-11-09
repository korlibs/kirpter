package com.soywiz.kirpter.proxy

import org.jetbrains.kotlin.com.intellij.mock.*
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*

class KirpterProxyCommandLineProcessor : CommandLineProcessor {
	override val pluginId: String get() = "Kirpter"
	override val pluginOptions: Collection<AbstractCliOption> get() = listOf()
}
