rootProject.name = "kotlin-marketplace"
include("les1hw1")
include("acceptence")

pluginManagement {
    val kotlinVersion: String by settings
    plugins {
        kotlin("jvm") version kotlinVersion apply false
    }
}

