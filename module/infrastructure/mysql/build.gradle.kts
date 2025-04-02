plugins {
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

dependencies {
    runtimeOnly("com.mysql:mysql-connector-j")
}