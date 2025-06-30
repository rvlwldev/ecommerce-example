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
    "http",
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
    "user" to "event-listener",
    "user" to "auth-service",
    "user" to "command-service",
    "user" to "command-persistence",
    "user" to "command-rest",
    "user" to "query-service",

    // point
    "point" to "domain",
    "point" to "command-service",

    // order
    "order" to "domain",
    "order" to "command-service",
    "order" to "command-rest",

    // product
    "product" to "domain",
    "product" to "query-service",
).forEach { (name, layer) ->
    include(":$name-$layer")
    project(":$name-$layer").projectDir = file("module/service/$name/$layer")
}