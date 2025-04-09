plugins {
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
//    implementation("org.springframework.boot:spring-boot-starter-security")

    implementation(project(":core-security"))

    implementation(project(":infrastructure-mysql"))
    implementation(project(":infrastructure-redis"))
    implementation(project(":infrastructure-kafka"))
    implementation(project(":infrastructure-monitoring"))

    implementation(project(":domain-member"))
    implementation(project(":domain-cart"))
}