plugins {
	id("com.github.gmazzo.buildconfig") version "3.0.3"
	`maven-publish`
	signing
	java
	kotlin("jvm")
}

val forcedVersion = System.getenv("FORCED_KORGE_PLUGINS_VERSION")

if (forcedVersion != null) {
	allprojects {
		this.version = forcedVersion
			.replace("refs/tags/v", "").replace("v", "")
			.takeIf { it.isNotEmpty() }
			?: project.version
	}
}

allprojects {
	group = "com.soywiz.kirpter"
	repositories {
		mavenLocal()
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "java")
	apply(plugin = "kotlin")
	apply(plugin = "signing")
	apply(plugin = "maven-publish")
	val signingSecretKeyRingFile = System.getenv("ORG_GRADLE_PROJECT_signingSecretKeyRingFile") ?: project.findProperty("signing.secretKeyRingFile")?.toString()

	// gpg --armor --export-secret-keys foobar@example.com | awk 'NR == 1 { print "signing.signingKey=" } 1' ORS='\\n'
	val signingKey = System.getenv("ORG_GRADLE_PROJECT_signingKey") ?: project.findProperty("signing.signingKey")?.toString()
	val signingPassword = System.getenv("ORG_GRADLE_PROJECT_signingPassword") ?: project.findProperty("signing.password")?.toString()

	if (signingSecretKeyRingFile != null || signingKey != null) {
		signing {
			this.isRequired = !project.version.toString().endsWith("-SNAPSHOT")
			if (signingKey != null) {
				useInMemoryPgpKeys(signingKey, signingPassword)
			}
			sign(publishing.publications)
		}
	}

	val sonatypePublishUser = (System.getenv("SONATYPE_USERNAME") ?: rootProject.findProperty("SONATYPE_USERNAME")?.toString() ?: project.findProperty("sonatypeUsername")?.toString())
	val sonatypePublishPassword = (System.getenv("SONATYPE_PASSWORD") ?: rootProject.findProperty("SONATYPE_PASSWORD")?.toString() ?: project.findProperty("sonatypePassword")?.toString())

	if (sonatypePublishUser == null || sonatypePublishPassword == null) {
		println("Required sonatypeUsername and sonatypePassword in ~/.gradle/gradle.properties")
	}
	val sourcesJar = tasks.create("sourcesJar", Jar::class) {
		archiveClassifier.set("sources")
		from(kotlin.sourceSets.flatMap { it.kotlin.srcDirs })
	}

	val javadocJar = tasks.create("javadocJar", Jar::class) {
		archiveClassifier.set("javadoc")
	}

	publishing {
		repositories {
			val GITHUB_REPOSITORY = System.getenv("GITHUB_REPOSITORY")
			val GITHUB_TOKEN = System.getenv("GITHUB_TOKEN")
			//def GITHUB_REPOSITORY = "korlibs/easy-kotlin-mpp-gradle-plugin"
			//def GITHUB_TOKEN = "test"
			if (GITHUB_REPOSITORY != null && GITHUB_TOKEN != null) {
				maven {
					name = "GitHubPackages"
					url = uri("https://maven.pkg.github.com/${GITHUB_REPOSITORY}")
					credentials(HttpHeaderCredentials::class) {
						name = "authorization"
						value = "Bearer ${GITHUB_TOKEN}"
					}
				}
			}

			if (sonatypePublishUser != null && sonatypePublishPassword != null) {
				maven {
					credentials {
						username = sonatypePublishUser
						password = sonatypePublishPassword
					}
					if (version.toString().contains("-SNAPSHOT")) {
						url = uri("https://oss.sonatype.org/content/repositories/snapshots/")
					} else {
						url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
					}
				}
			}
		}
		publications {
			create<MavenPublication>("maven") {
				groupId = project.group.toString()
				artifactId = project.name
				version = project.version.toString()

				from(components["java"])
				artifact(sourcesJar)
				artifact(javadocJar)

				pom {
					name.set(project.name)
					description.set(project.property("project.description").toString())
					url.set(project.property("project.scm.url").toString())
					developers {
						developer {
							id.set(project.property("project.author.id").toString())
							name.set(project.property("project.author.name").toString())
							email.set(project.property("project.author.email").toString())
						}
					}
					licenses {
						license {
							name.set(project.property("project.license.name").toString())
							url.set(project.property("project.license.url").toString())
						}
					}
					scm {
						url.set(project.property("project.scm.url").toString())
					}
				}
			}
		}
	}
}

val kotlinVersion: String by project
val pluginID: String by project

project(":kirpter-api") {
	description = "Kotlin IR Plugin Adapter API"

	dependencies {
		api(kotlin("stdlib"))
		//compileOnlyApi("org.jetbrains.kotlin:kotlin-compiler-embeddable")
		api("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
		testImplementation(kotlin("test-junit"))
		testImplementation("org.jetbrains.kotlin:kotlin-compiler-embeddable:$kotlinVersion")
		testImplementation("com.github.tschuchortdev:kotlin-compile-testing:1.4.5")
	}
}

project(":kirpter-proxy") {

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

/*
tasks.create("externalReleaseMavenCentral", GradleBuild::class) {
	dependsOn("publishToMavenLocal")
	tasks = listOf("releaseMavenCentral")
	var tempDir: File? = null
	doFirst {
		tempDir = File.createTempDir()
		println("Created dir $tempDir...")
		dir(tempDir)
		File(tempDir, "settings.gradle").writeText("")
		File(tempDir, "build.gradle").writeText("""
			\tbuildscript {
			\t\trepositories {
			\t\t\tmavenLocal()
			\t\t\tmavenCentral()
			\t\t\tgoogle()
			\t\t\tmaven { url = uri("https://plugins.gradle.org/m2/") }
			\t\t}
			\t\tdependencies {
			\t\t\tclasspath("com.soywiz.korlibs:easy-kotlin-mpp-gradle-plugin:${project.version}")
			\t\t}
			\t}
			
			project.group = '${project.group}'
			\tapply plugin: "com.soywiz.korlibs.easy-kotlin-mpp-gradle-plugin"
		""".trimIndent())
	}
	doLast {
		tempDir?.deleteDir()
	}
}
 */