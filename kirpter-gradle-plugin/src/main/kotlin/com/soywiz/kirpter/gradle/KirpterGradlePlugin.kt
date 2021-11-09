package com.soywiz.kirpter.gradle

import org.gradle.api.*
import org.gradle.api.artifacts.*
import org.gradle.api.provider.*
import org.jetbrains.kotlin.gradle.plugin.*
import java.util.logging.*

@Suppress("unused")
class KirpterGradlePlugin : KotlinCompilerPluginSupportPlugin {
	override fun apply(target: Project) {
		target.logger.info("KirpterGradlePlugin.apply")
		target.configurations.maybeCreate("kirpter")
	}

	override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean = true

	override fun getCompilerPluginId(): String = BuildConfig.KOTLIN_PLUGIN_ID

	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(
			groupId = BuildConfig.ARTIFACT_GROUP,
			artifactId = BuildConfig.ARTIFACT_NAME,
			version = BuildConfig.ARTIFACT_VERSION
		)
	}

	override fun getPluginArtifactForNative(): SubpluginArtifact = getPluginArtifact()

	override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
		val target = kotlinCompilation.target
		val project = target.project
		project.logger.info("KDynLibGradlePlugin.applyToCompilation: '${target.platformType}'")

		val options = listOf(
			SubpluginOption("targetName", target.targetName),
			SubpluginOption("platformType", target.platformType.toString()),
			FilesSubpluginOption("apclasspath", project.configurations.maybeCreate("kirpter").toList()),
		)

		val config: Configuration = project.configurations.maybeCreate("kirpter")

		//println(config.buildDependencies)

		kotlinCompilation.compileKotlinTask.dependsOn(config.buildDependencies)

		//println("applyToCompilation: $options")
		//println(": ${project.configurations.maybeCreate("kirpter").toList()}")

		return project.provider { options }
	}
}