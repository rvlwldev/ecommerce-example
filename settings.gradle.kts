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

// core
include(":core-exception").apply { project(":core-exception").projectDir = file("module/core/exception") }
include(":core-security").apply { project(":core-security").projectDir = file("module/core/security") }

// infrastructure
include(":infrastructure-mysql").apply { project(":infrastructure-mysql").projectDir = file("module/infrastructure/mysql") }
include(":infrastructure-redis").apply { project(":infrastructure-redis").projectDir = file("module/infrastructure/redis") }
include(":infrastructure-kafka").apply { project(":infrastructure-kafka").projectDir = file("module/infrastructure/kafka") }
include(":infrastructure-monitoring").apply { project(":infrastructure-monitoring").projectDir = file("module/infrastructure/monitoring") }

// testcontainers
include(":testcontainer-redis").apply { project(":testcontainer-redis").projectDir = file("module/infrastructure/testcontainer/redis") }

// domain
include(":domain-member").apply { project(":domain-member").projectDir = file("module/domain/member") }
include(":domain-address").apply { project(":domain-address").projectDir = file("module/domain/address") }