plugins {
	id("com.github.gmazzo.buildconfig") version "3.0.3" apply false
}

allprojects {
	group = "com.soywiz.kirpter"
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

/*
//buildConfig {
//    val project = this
//    packageName(project.group.toString())
//    buildConfigField("String", "PROJECT_GROUP_ID", "\"${project.group}\"")
//    buildConfigField("String", "PROJECT_ARTIFACT_ID", "\"${project.name}\"")
//    buildConfigField("String", "PROJECT_VERSION", "\"${project.version}\"")
//}

*/
