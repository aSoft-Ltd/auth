plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    multiplatformLib(forNodeJs = false)
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(asoft("persist-client", vers.asoft.persist))
                api(asoft("persist-keyvalue-locally", vers.asoft.persist))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(asoft("kotlinx-serialization-mapper", vers.asoft.mapper))
                api(asoft("later-ktx", vers.asoft.later))
                api(project(":auth-client-core"))
                api(project(":authentication-core"))
                api(project(":authorization-client-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":authentication-test-data"))
                implementation(asoft("expect-coroutines", vers.asoft.expect))
                implementation(asoft("viewmodel-test-expect", vers.asoft.viewmodel))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authentication on the client"
)