plugins {
}

allprojects {
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

group = "com.soywiz.kotlin-ir-plugin-adapter-gradle"

/*
//buildConfig {
//    val project = this
//    packageName(project.group.toString())
//    buildConfigField("String", "PROJECT_GROUP_ID", "\"${project.group}\"")
//    buildConfigField("String", "PROJECT_ARTIFACT_ID", "\"${project.name}\"")
//    buildConfigField("String", "PROJECT_VERSION", "\"${project.version}\"")
//}

*/
