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
                api(asoft("files", vers.asoft.files))
                api(asoft("persist-core", vers.asoft.persist))
                api(asoft("krypto-core", vers.asoft.krypto))
                api(asoft("either-core", vers.asoft.duality))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(project(":authorization-core"))
                api(asoft("access-system", vers.asoft.access))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authentication"
)