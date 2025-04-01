import org.gradle.kotlin.dsl.libs

plugins {
    id("jacoco")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

subprojects {
    apply {
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.jvm")
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
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        implementation("com.h2database:h2")

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

    plugins.withId("org.springframework.boot") {
        tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
            enabled = false
        }
        tasks.named<Jar>("jar") {
            enabled = true
        }
    }

    dependencies {
        add("implementation", "org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        add("implementation", "org.jetbrains.kotlin:kotlin-reflect")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}