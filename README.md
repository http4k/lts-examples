# http4k LTS examples

This repo contains instructions for customers of the http4k LTS programme.

## Accessing the LTS

The LTS version of the http4k libraries are hosted in a private Maven repository, at https://maven.http4k.org. Access to these artifacts requires credentials that will be issued with your LTS subscription.

Configuring Gradle to use the private Maven repo can be done by using a custom repository: 

```kotlin
val ltsMavenUser: String by project
val ltsMavenPassword: String by project

repositories {
    maven {
        credentials {
            username = ltsMavenUser
            password = ltsMavenPassword
        }
        url = URI("https://maven.http4k.org")
    }

    mavenCentral()
}
```

To set up access in your Gradle build, you will need to pass the credentials in some fashion. The easiest way to do this is to put them in your `gradle.properties` or to pass them on the command line. The properties are `ltsMavenUser` and `ltsMavenPassword`:

```shell
./gradlew -PltsMavenUser=lts_username -PltsMavenPassword=secret_password check
```
