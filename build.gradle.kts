import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    kotlin("jvm")
}

allprojects {
    group = "ru.khrebtov"
    version = "1.0-SNAPSHOT"

    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
}
