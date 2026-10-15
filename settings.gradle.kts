pluginManagement {
    repositories {
        maven {
            credentials {
                username = providers.gradleProperty("eeMavenUser").orElse(providers.environmentVariable("HTTP4K_MAVEN_USER")).orNull
                password = providers.gradleProperty("eeMavenPassword").orElse(providers.environmentVariable("HTTP4K_MAVEN_PASSWORD")).orNull
            }
            url = uri("https://maven.http4k.org")
        }
        gradlePluginPortal()
    }
}

rootProject.name = "http4k-ee-examples"
