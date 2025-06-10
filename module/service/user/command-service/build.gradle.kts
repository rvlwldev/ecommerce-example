dependencies {
    implementation(project(":core-exception"))
    implementation(project(":core-security"))

    implementation(project(":user-domain"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}