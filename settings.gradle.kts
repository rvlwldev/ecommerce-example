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

// main
include(":main-application")
project(":main-application").projectDir = file("module/application")

// core
listOf(
    "exception",
    "security",
).forEach { name ->
    include(":core-$name")
    project(":core-$name").projectDir = file("module/core/$name")
}

// common
listOf(
    "mysql",
    "redis",
    "kafka",
    /* monitoring */
).forEach { name ->
    include(":common-$name")
    project(":common-$name").projectDir = file("module/common/$name")
}

// test
listOf("redis").forEach { name ->
    include(":test-$name")
    project(":test-$name").projectDir = file("module/test/$name")
}

// service
listOf(
    // user
    "user" to "domain",
    "user" to "service",
    "user" to "persistence",
    "user" to "rest",
    "user" to "event-listener",

    // point
    "point" to "domain",
).forEach { (name, layer) ->
    include(":$name-$layer")
    project(":$name-$layer").projectDir = file("module/service/$name/$layer")
}