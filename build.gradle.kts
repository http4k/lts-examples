import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.net.URI

plugins {
    kotlin("jvm") version "2.0.21"
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

val ltsUser: String by project
val ltsPassword: String by project

repositories {
    maven {
        credentials {
            username = ltsUser
            password = ltsPassword
        }
        url = URI("https://maven.http4k.org")
    }

    mavenCentral()
}

tasks {
    withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            allWarningsAsErrors = false
            jvmTarget.set(JVM_11)
            freeCompilerArgs.add("-Xjvm-default=all")
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }

    java {
        sourceCompatibility = VERSION_11
        targetCompatibility = VERSION_11
    }
}

dependencies {
    val http4kVersion: String by project

    implementation(platform("org.http4k:http4k-bom:$http4kVersion"))
    implementation("org.http4k:http4k-core")

    testImplementation("org.http4k:http4k-testing-hamkrest")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.1")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.11.1")
}
