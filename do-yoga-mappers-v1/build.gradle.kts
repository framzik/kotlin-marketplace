plugins {
    kotlin("jvm")
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":do-yoga-api-v1-jackson"))
    implementation(project(":do-yoga-common"))

    testImplementation(kotlin("test-junit"))
}