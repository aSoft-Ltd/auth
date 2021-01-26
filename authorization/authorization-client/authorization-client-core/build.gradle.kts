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
                api(asoft("krypto-core", vers.asoft.krypto))
                api(asoft("viewmodel-core", vers.asoft.viewmodel))
                api(asoft("persist-client", vers.asoft.persist))
                api(project(":auth-client-core"))
                api(project(":authorization-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
                implementation(project(":authorization-test-data"))
                implementation(asoft("expect-coroutines", vers.asoft.expect))
                implementation(asoft("viewmodel-test-expect", vers.asoft.viewmodel))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authorization on the client"
)