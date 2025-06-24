dependencies {
    implementation(project(":common-redis"))

    implementation(project(":user-domain"))
    implementation(project(":user-command-service"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.mapstruct:mapstruct:1.6.3")
}