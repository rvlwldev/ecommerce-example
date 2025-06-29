dependencies {
    implementation(project(":core-exception"))

    api(project(":order-domain"))
    implementation(project(":user-query-service"))
    implementation(project(":product-query-service"))
    implementation(project(":point-command-service"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.4.0")
}