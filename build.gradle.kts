import org.gradle.kotlin.dsl.libs
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("jacoco")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

subprojects {
    apply {
        plugin("jacoco")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.jpa")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    extensions.configure<JavaPluginExtension>("java") {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
        ignoreFailures = true
    }

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            javaParameters.set(true)
        }
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

        add("testImplementation", "org.jetbrains.kotlin:kotlin-test-junit5")
        add("testImplementation", "org.mockito:mockito-core")
    }
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}

tasks.named<Jar>("jar") {
    enabled = true
}