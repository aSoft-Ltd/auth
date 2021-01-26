plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    multiplatformLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":authentication-client-core"))
                api(asoft("rest-client-ktor", vers.asoft.rest))
//                api(project(":rest-client-ktor"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-coroutines", vers.asoft.test))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authentication-services on the client with ktor client"
)