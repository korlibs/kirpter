plugins {
	java
	`java-gradle-plugin`
	kotlin("jvm")
	`maven-publish`
	//id("com.gradle.plugin-publish")
	//id("com.github.gmazzo.buildconfig")
}

description = "Kotlin IR Plugin Adapter Gradle Plugin"

dependencies {
	api(kotlin("stdlib"))
	implementation(kotlin("gradle-plugin-api"))
	compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation(kotlin("test-junit"))
}

sourceSets.main {
	java.srcDirs("src/main/kotlin")
}

gradlePlugin {
	plugins {
		create("kotlinReactFunction") {
			id = "com.soywiz.kirpter"
			displayName = "kirpter"
			description = project.description
			implementationClass = "com.soywiz.kirpter.gradle.KirpterGradlePlugin"
		}
	}
}
