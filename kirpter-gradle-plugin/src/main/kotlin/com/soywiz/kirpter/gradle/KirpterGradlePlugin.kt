package com.soywiz.kirpter.gradle

import org.gradle.api.*
import org.gradle.api.provider.*
import org.jetbrains.kotlin.gradle.plugin.*

class KirpterGradlePlugin : KotlinCompilerPluginSupportPlugin {
	override fun apply(target: Project) {
	}

	override fun getCompilerPluginId(): String = "com.soywiz.kirpter"

	override fun isApplicable(kotlinCompilation: KotlinCompilation<*>): Boolean {
		//println("KDynLibGradlePlugin.isApplicable: $kotlinCompilation")
		return true
		//return kotlinCompilation.platformType == KotlinPlatformType.js
	}

	val VERSION = "2.0.0.999" // @TODO: Fix this

	override fun getPluginArtifact(): SubpluginArtifact {
		return SubpluginArtifact(
			groupId = "com.soywiz.kirpter",
			artifactId = "kirpter-proxy",
			version = VERSION
		)
	}

	override fun getPluginArtifactForNative(): SubpluginArtifact = getPluginArtifact()

	override fun applyToCompilation(kotlinCompilation: KotlinCompilation<*>): Provider<List<SubpluginOption>> {
		//println("KDynLibGradlePlugin.applyToCompilation")
		kotlinCompilation.dependencies {
			implementation("com.soywiz.korlibs.kdynlib:kdynlib-jvm:$VERSION")
		}
		val project = kotlinCompilation.target.project
		return project.provider { listOf(SubpluginOption("targetName", kotlinCompilation.target.name)) }
	}
}