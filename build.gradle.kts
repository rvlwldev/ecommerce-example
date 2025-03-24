plugins {
    id("jacoco")
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
}

subprojects {
    apply {
        plugin("jacoco")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    extensions.configure<JavaPluginExtension>("java") {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    dependencies {
        // core
        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        add("implementation", "org.jetbrains.kotlin:kotlin-reflect")
        add("implementation", "org.jetbrains.kotlinx:kotlinx-coroutines-core")
        add("implementation", "com.fasterxml.jackson.module:jackson-module-kotlin")

        // spring boot
        add("implementation", "org.springframework.boot:spring-boot-starter-data-redis")
        add("implementation", "org.springframework.kafka:spring-kafka")

        // development
        add("developmentOnly", "org.springframework.boot:spring-boot-devtools")

        // test
        add("testImplementation", "org.springframework.boot:spring-boot-starter-test")
        add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit5")
        add("testImplementation", "org.jetbrains.kotlinx:kotlinx-coroutines-test")
        add("testImplementation", "org.mockito.kotlin:mockito-kotlin:4.0.0")
        add("testImplementation", "org.springframework.kafka:spring-kafka-test")
        add("testImplementation", "org.testcontainers:testcontainers")
        add("testImplementation", "com.redis:testcontainers-redis")
        add("testImplementation", "org.testcontainers:kafka")
        add("testImplementation", "org.testcontainers:junit-jupiter")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        ignoreFailures = true
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}