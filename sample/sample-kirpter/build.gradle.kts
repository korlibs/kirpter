val kirpterVersion: String by project

plugins {
	kotlin("jvm")
}

dependencies {
	api("com.soywiz.kirpter:kirpter-api:$kirpterVersion")
}
