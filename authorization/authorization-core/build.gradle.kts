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
                api(kotlinx("serialization-json", vers.kotlinx.serialization))
                api(asoft("krypto-core", vers.asoft.krypto))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(asoft("access-system", vers.asoft.access))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authorization"
)
