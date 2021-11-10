plugins {
	id("com.github.gmazzo.buildconfig")
}

val kotlinVersion: String by project
val pluginID: String by project

description = "Kotlin IR Plugin Adapter Proxy"

apply(plugin = "com.github.gmazzo.buildconfig")

dependencies {
	api(project(":kirpter-api"))
	api(kotlin("stdlib"))
	testImplementation(kotlin("test-junit"))
	testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
	testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
}

buildConfig {
	packageName("com.soywiz.kirpter.proxy")
	buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"$pluginID\"")
}
