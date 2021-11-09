rootProject.name = "kirpter-sample"

include(":sample-kirpter")
include(":sample")

pluginManagement {
	repositories {
		mavenLocal()
		mavenCentral()
		gradlePluginPortal()

		//val gradleProperties = java.util.Properties().also { it.load(File(rootDir, "gradle.properties").readText().reader()) }
		//val kotlinVersion = gradleProperties["kotlinVersion"].toString()
		val kotlinVersion: String by settings

		if (kotlinVersion.contains("-M") || kotlinVersion.contains("-dev") || kotlinVersion.contains("-RC") || kotlinVersion.contains("eap") || kotlinVersion.contains("-release")) {
			maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/temporary")
			maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-coroutines/maven")
			maven("https://maven.pkg.jetbrains.space/kotlin/p/kotlin/bootstrap/")
		}
		maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots/") }
	}
	val kotlinVersion: String by settings
	val kirpterVersion: String by settings
	plugins {
		id("com.soywiz.kirpter") version kirpterVersion
		kotlin("jvm") version kotlinVersion
	}
}
