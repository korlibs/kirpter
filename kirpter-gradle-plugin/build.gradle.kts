plugins {
	`java-gradle-plugin`
	id("com.github.gmazzo.buildconfig")
}

description = "Kotlin IR Plugin Adapter Gradle Plugin"

dependencies {
	api(project(":kirpter-api"))
	api(kotlin("stdlib"))
	implementation(kotlin("gradle-plugin-api"))
	compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation(kotlin("test-junit"))
}

sourceSets.main {
	java.srcDirs("src/main/kotlin")
}

val pluginID: String by project

gradlePlugin {
	plugins {
		create("kirpter") {
			id = pluginID
			displayName = "kirpter"
			description = project.description
			implementationClass = "com.soywiz.kirpter.gradle.KirpterGradlePlugin"
		}
	}
}

buildConfig {
	packageName("com.soywiz.kirpter.gradle")
	val proxy = project(":kirpter-proxy")
	buildConfigField("String", "KOTLIN_PLUGIN_ID", "\"$pluginID\"")
	buildConfigField("String", "ARTIFACT_GROUP", "\"${proxy.group}\"")
	buildConfigField("String", "ARTIFACT_NAME", "\"${proxy.name}\"")
	buildConfigField("String", "ARTIFACT_VERSION", "\"${proxy.version}\"")
}
