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
include(":main-application").apply { project(":main-application").projectDir = file("module/application") }

// core
include(":core-security").apply { project(":core-security").projectDir = file("module/core/security") }

// infra
include(":infra-mysql").apply { project(":infra-mysql").projectDir = file("module/infrastructure/mysql") }
include(":infra-redis").apply { project(":infra-redis").projectDir = file("module/infrastructure/redis") }
include(":infra-kafka").apply { project(":infra-kafka").projectDir = file("module/infrastructure/kafka") }

// domain
include(":domain-member").apply { project(":domain-member").projectDir = file("module/domain/member") }
include(":domain-address").apply { project(":domain-address").projectDir = file("module/domain/address") }