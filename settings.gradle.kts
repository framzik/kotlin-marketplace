rootProject.name = "kotlin-marketplace"
include("les1hw1")
include("acceptence")
include("do-yoga-api-v1-jackson")
include("do-yoga-common")
include("do-yoga-mappers-v1")
include("do-yoga-stubs")
include("do-yoga-biz")
include("class-app-spring")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    val springframeworkBootVersion: String by settings
    val springDependencyManagementVersion: String by settings
    val pluginSpringVersion: String by settings
    val pluginJpa: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.springframework.boot") version springframeworkBootVersion apply false
        id("io.spring.dependency-management") version springDependencyManagementVersion apply false
        kotlin("plugin.spring") version pluginSpringVersion apply false
        kotlin("plugin.jpa") version pluginJpa apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

