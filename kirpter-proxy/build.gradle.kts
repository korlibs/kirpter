plugins {
	java
	kotlin("jvm")
	`maven-publish`
	id("com.github.gmazzo.buildconfig")
}

description = "Kotlin IR Plugin Adapter Proxy"

val kotlinVersion: String by project

dependencies {
	api(project(":kirpter-api"))
	api(kotlin("stdlib"))
	testImplementation(kotlin("test-junit"))
	testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
	testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
}

val pluginID: String by project

buildConfig {
	packageName("com.soywiz.kirpter.proxy")
	buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"$pluginID\"")
}

publishing {
	publications {
		create<MavenPublication>("maven") {
			groupId = project.group.toString()
			artifactId = project.name
			version = project.version.toString()

			from(components["java"])
		}
	}
}
