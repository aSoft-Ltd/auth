plugins {
    kotlin("jvm")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    target {
        targetJava("1.8")
    }

    sourceSets {
        val main by getting {
            dependencies {
                api(project(":authentication-server-core"))
                api(asoft("rest-server-ktor", vers.asoft.rest))
//                api(project(":rest-server-ktor"))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authentication on restfull server with ktor"
)