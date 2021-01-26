plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    multiplatformLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":authorization-core"))
                api(asoft("jwt-rs", vers.asoft.jwt))
                api(asoft("persist-inmemory", vers.asoft.persist))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-coroutines", vers.asoft.test))
                implementation(asoft("viewmodel-test-expect", vers.asoft.viewmodel))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authorization persistence on a restfull api"
)