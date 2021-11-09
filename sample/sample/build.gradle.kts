val kirpterVersion: String by project

plugins {
	kotlin("jvm")
	id("com.soywiz.kirpter")
	application
}

dependencies {
	api(kotlin("stdlib"))
	kirpter(project(":sample-kirpter"))
}

application {
	mainClass.set("sample.MainKt")
}