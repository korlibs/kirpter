plugins {
	java
	kotlin("jvm")
	`maven-publish`
}

description = "Kotlin IR Plugin Adapter API"
val kotlinVersion: String by project

dependencies {
	api(kotlin("stdlib"))
	//compileOnlyApi("org.jetbrains.kotlin:kotlin-compiler-embeddable")
	api("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
	testImplementation(kotlin("test-junit"))
	testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
	testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
}
