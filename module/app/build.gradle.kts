plugins {
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":core-security"))

    implementation(project(":common-mysql"))
    implementation(project(":common-redis"))
    implementation(project(":common-kafka"))
    implementation(project(":common-monitoring"))

    implementation(project(":member-rest-controller"))
}