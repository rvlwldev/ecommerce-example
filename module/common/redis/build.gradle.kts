dependencies {
    implementation("org.springframework.boot:spring-boot-starter-aop")

    api("org.springframework.boot:spring-boot-starter-data-redis")

    testImplementation("org.springframework:spring-test")
    testImplementation(project(":test-redis"))
}