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
                api(asoft("jwt-hs", vers.asoft.jwt))
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
    description = "A Kotlin Multiplatform library to handle authentication persistence in memory"
)
