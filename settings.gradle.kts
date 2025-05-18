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
include(":application").apply { project(":application").projectDir = file("module/app") }

// test
include(":test-redis").apply { project(":test-redis").projectDir = file("module/test/redis") }

// core
include(":core-exception").apply { project(":core-exception").projectDir = file("module/core/exception") }
include(":core-security").apply { project(":core-security").projectDir = file("module/core/security") }

// common
include(":common-mysql").apply { project(":common-mysql").projectDir = file("module/common/mysql") }
include(":common-redis").apply { project(":common-redis").projectDir = file("module/common/redis") }
include(":common-kafka").apply { project(":common-kafka").projectDir = file("module/common/kafka") }
include(":common-monitoring").apply { project(":common-monitoring").projectDir = file("module/common/monitoring") }
include(":common-web").apply { project(":common-web").projectDir = file("module/common/web") }

// member
listOf("rest-controller", "event-listener", "service", "persistence")
    .forEach { name ->
        include(":member-$name").apply {
            project(":member-$name").projectDir = file("module/domain/member/$name")
        }
    }

include(":domain-address").apply { project(":domain-address").projectDir = file("module/domain/address") }