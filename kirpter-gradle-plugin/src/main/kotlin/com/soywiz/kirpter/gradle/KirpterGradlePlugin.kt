package com.soywiz.kirpter.gradle

import org.gradle.api.*
import org.gradle.api.provider.*
import org.jetbrains.kotlin.gradle.plugin.*

class KirpterGradlePlugin : KotlinCompilerPluginSupportPlugin {
	override fun apply(target: Project) {
		target.configurations.maybeCreate("kripter")
	}

	override fun getCompilerPluginId(): String = "com.soywiz.kirpter"

	override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
		return true
	}

	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(
			groupId = BuildConfig.ARTIFACT_GROUP,
			artifactId = BuildConfig.ARTIFACT_NAME,
			version = BuildConfig.ARTIFACT_VERSION
		)
	}

	override fun getPluginArtifactForNative(): SubpluginArtifact = getPluginArtifact()

	override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
		val project = kotlinCompilation.target.project
		//println("KDynLibGradlePlugin.applyToCompilation")
		kotlinCompilation.dependencies {
			//implementation("com.soywiz.korlibs.kdynlib:kdynlib-jvm:$VERSION")
		}
		return project.provider {
			listOf(
				SubpluginOption("targetName", kotlinCompilation.target.name),
				FilesSubpluginOption("apclasspath", project.configurations.maybeCreate("kripter").toList()),
			)
		}
	}
}