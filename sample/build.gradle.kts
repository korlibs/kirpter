plugins {
	kotlin("jvm") apply false
	id("com.gradle.plugin-publish") version "0.16.0" apply false
	id("com.github.gmazzo.buildconfig") version "3.0.3" apply false
}

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
	}
}
