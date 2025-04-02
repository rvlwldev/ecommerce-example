rootProject.name = "ecommerce-practice"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
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
    ":address" to "module/address",
).forEach { (name, path) ->
    include(name)
    project(name).projectDir = file(path)
}
