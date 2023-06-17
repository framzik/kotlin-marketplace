rootProject.name = "kotlin-marketplace"
include("les1hw1")
include("acceptence")
include("do-yoga-api-v1-jackson")
include("do-yoga-common")
include("do-yoga-mappers-v1")

pluginManagement {
    val kotlinVersion: String by settings
    val openapiVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
        kotlin("plugin.serialization") version kotlinVersion apply false

        id("org.openapi.generator") version openapiVersion apply false
    }
}

