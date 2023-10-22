plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm {}
    macosX64 {}
    linuxX64 {}

    sourceSets {
        val coroutinesVersion: String by project

        all { languageSettings.optIn("kotlin.RequiresOptIn") }

        @Suppress("UNUSED_VARIABLE")
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation(project(":do-yoga-common"))
                implementation(project(":do-yoga-stubs"))
                implementation(project(":do-yoga-lib-cor"))

                implementation(project(":do-yoga-repo-tests"))
                implementation(project(":do-yoga-repo-stubs"))
                implementation(project(":do-yoga-repo-in-memory"))
                implementation(project(":do-yoga-auth"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))

                api("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
            }
        }
        @Suppress("UNUSED_VARIABLE")
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}
