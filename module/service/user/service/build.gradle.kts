dependencies {
    implementation(project(":core-exception"))
    implementation(project(":core-security"))

    implementation(project(":common-redis"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}