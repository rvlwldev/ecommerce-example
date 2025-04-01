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

include(":module-product")
project(":module-product").projectDir = file("module/product")
listOf(
    ":core-security" to "module/core-security"
).forEach { (name, path) ->
    include(name)
    project(name).projectDir = file(path)
}
