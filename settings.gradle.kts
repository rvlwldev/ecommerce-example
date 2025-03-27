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
    ":member-domain" to "module/member-domain",
    ":member-service" to "module/member-service",
    ":member-infra-main" to "module/member-infra-main",
    ":member-infra-jpa" to "module/member-infra-jpa"
).forEach { (name, path) ->
    include(name)
    project(name).projectDir = file(path)
}

