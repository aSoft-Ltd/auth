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
                api(project(":authentication-core"))
                api(project(":authorization-server-core"))
//                api(project(":rest-server-core"))
                api(asoft("rest-server-core", vers.asoft.rest))
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
    description = "A Kotlin Multiplatform library to handle authentication on a restfull server"
)