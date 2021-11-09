plugins {
	java
	`java-gradle-plugin`
	kotlin("jvm")
	`maven-publish`
	id("com.github.gmazzo.buildconfig")
}

description = "Kotlin IR Plugin Adapter Proxy"

dependencies {
	api(project(":kirpter-api"))
	api(kotlin("stdlib"))
	compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation(kotlin("test-junit"))
	testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
}

val pluginID: String by project

buildConfig {
	packageName("com.soywiz.kirpter.proxy")
	buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"$pluginID\"")
}
