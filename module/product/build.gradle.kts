import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
    ignoreFailures = true
}
