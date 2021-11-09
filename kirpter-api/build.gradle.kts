plugins {
	java
	`java-gradle-plugin`
	kotlin("jvm")
	`maven-publish`
	//id("com.gradle.plugin-publish")
	//id("com.github.gmazzo.buildconfig")
}

description = "Kotlin IR Plugin Adapter API"

dependencies {
	api(kotlin("stdlib"))
	compileOnly("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation(kotlin("test-junit"))
	testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
}
