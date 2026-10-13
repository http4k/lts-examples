import org.gradle.api.JavaVersion.VERSION_21
import org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.net.URI

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.versionCatalogUpdate)
    alias(libs.plugins.versions)
    alias(libs.plugins.verify)
    alias(libs.plugins.typeflows)
}

buildscript {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

java {
    sourceCompatibility = VERSION_21
    targetCompatibility = VERSION_21
}

val eeMavenUser: String = project.findProperty("eeMavenUser") as? String ?: System.getenv("LTS_MAVEN_USER")
val eeMavenPassword: String = project.findProperty("eeMavenPassword") as? String ?: System.getenv("LTS_MAVEN_PASSWORD")

repositories {
    maven {
        credentials {
            username = eeMavenUser
            password = eeMavenPassword
        }
        url = URI("https://maven.http4k.org")
    }

    mavenCentral()
}

tasks {
    withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            allWarningsAsErrors = false
            jvmTarget.set(JVM_21)
        }
    }

    withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform(libs.http4k.bom))
    implementation(libs.http4k.core)
    testImplementation(libs.http4k.testing.hamkrest)
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly(libs.junit.platform.launcher)

    typeflowsApi(libs.typeflows.github)
    typeflowsApi(libs.typeflows.github.marketplace)
    typeflowsApi(libs.http4k.standards)
}
