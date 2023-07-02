plugins {
    kotlin("jvm")
    java
    id ("com.bmuschko.docker-java-application")
}

dependencies {
    val rabbitVersion: String by project
    val jacksonVersion: String by project
    val logbackVersion: String by project
    val coroutinesVersion: String by project
    val testContainersVersion: String by project

    implementation(kotlin("stdlib"))
    implementation("com.rabbitmq:amqp-client:$rabbitVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation(project(":do-yoga-common"))
    implementation(project(":do-yoga-api-v1-jackson"))
    implementation(project(":do-yoga-mappers-v1"))

    implementation(project(":do-yoga-biz"))

    testImplementation("org.testcontainers:rabbitmq:$testContainersVersion")
    testImplementation(kotlin("test"))
    testImplementation(project(":do-yoga-stubs"))
}

docker {
    javaApplication {

        baseImage.set("openjdk:17")
        ports.set(listOf(8080,5672))
        images.set(setOf("${project.name}:latest"))
        jvmArgs.set(listOf("-XX:+UseContainerSupport"))
    }
}
