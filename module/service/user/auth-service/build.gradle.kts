dependencies {
    implementation(project(":user-domain"))
    implementation(project(":user-command-persistence"))

    implementation("org.springframework:spring-tx")
    api("org.springframework.boot:spring-boot-starter-security")
}