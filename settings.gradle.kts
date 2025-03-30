rootProject.name = "ecommerce-practice"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.9.0"
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage") repositories {
        mavenCentral()
    }
}

include(":module-api")
project(":module-api").projectDir = file("module/api")

listOf(
    ":core-security" to "module/core-security",
    ":member" to "module/member",
).forEach { (name, path) ->
    include(name)
    project(name).projectDir = file(path)
}
