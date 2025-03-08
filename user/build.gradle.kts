import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    kotlin("plugin.jpa") version "2.1.10"
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    // kotlin
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")

    // service
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.kafka:spring-kafka")
    implementation("com.mysql:mysql-connector-j")

    // test
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    testImplementation("org.springframework.kafka:spring-kafka-test")
    testImplementation("org.testcontainers:testcontainers")
    testImplementation("com.redis:testcontainers-redis")
    testImplementation("org.testcontainers:kafka")
    testImplementation("org.testcontainers:junit-jupiter")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.addAll("-Xjsr305=strict", "-language-version=2.0")
    }
}

tasks.getByName("bootJar") {
    enabled = true
}
tasks.getByName("jar") {
    enabled = false
}
tasks.test {
    ignoreFailures = true
    useJUnitPlatform()
}
