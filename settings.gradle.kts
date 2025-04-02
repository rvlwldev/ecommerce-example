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

// infrastructure
include(":infrastructure-mysql").apply { project(":infrastructure-mysql").projectDir = file("module/infrastructure/mysql") }
include(":infrastructure-redis").apply { project(":infrastructure-redis").projectDir = file("module/infrastructure/redis") }
include(":infrastructure-kafka").apply { project(":infrastructure-kafka").projectDir = file("module/infrastructure/kafka") }

// domain
include(":domain-member").apply { project(":domain-member").projectDir = file("module/domain/member") }
include(":domain-address").apply { project(":domain-address").projectDir = file("module/domain/address") }