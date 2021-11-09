val kirpterVersion: String by project

plugins {
	kotlin("jvm")
	kotlin("kapt")
}

dependencies {
	api(kotlin("stdlib"))
	api("com.soywiz.kirpter:kirpter-api:$kirpterVersion")

	// autoservice
	kapt("com.google.auto.service:auto-service:1.0")
	compileOnly("com.google.auto.service:auto-service-annotations:1.0")
}
