dependencies {
    implementation(project(":core-exception"))

    api(project(":product-domain"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-tx")
}