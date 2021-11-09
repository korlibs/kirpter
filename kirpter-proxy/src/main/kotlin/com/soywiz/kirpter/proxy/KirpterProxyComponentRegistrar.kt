package com.soywiz.kirpter.proxy

import com.soywiz.kirpter.api.*
import org.jetbrains.kotlin.backend.common.extensions.*
import org.jetbrains.kotlin.cli.common.*
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.cli.jvm.plugins.*
import org.jetbrains.kotlin.com.intellij.mock.*
import org.jetbrains.kotlin.compiler.plugin.*
import org.jetbrains.kotlin.config.*
import java.net.*
import java.util.*

class KirpterProxyComponentRegistrar : ComponentRegistrar {
	override fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration) {
		val config = configuration[KirpterConfig.KEY, KirpterConfig()]
		val classPathURLs = config.processingClasspath.map { it.toURI().toURL() }
		val classLoader = URLClassLoader(classPathURLs.toTypedArray(), this.javaClass.classLoader)
		try {
			val plugins = ServiceLoaderLite.loadImplementations(KirpterPlugin::class.java, classLoader)
			val context = KirpterContext(configuration, config, project)

			for (plugin in plugins) {
				plugin.createGeneratorExtension(context)?.let { IrGenerationExtension.registerExtension(project, it) }
			}
		} finally {
			//classLoader.close()
		}
	}
}