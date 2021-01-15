plugins {
    kotlin("multiplatform")
    id("tz.co.asoft.library")
}

kotlin {
    multiplatformLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":authorization-server-ktor"))
                api(project(":authorization-test-data"))
            }
        }
    }
}