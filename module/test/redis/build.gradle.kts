plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation(project(":common-redis"))

    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.testcontainers:junit-jupiter")
    implementation("org.testcontainers:testcontainers")
}