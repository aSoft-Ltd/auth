plugins {
    kotlin("js")
    id("tz.co.asoft.library")
    id("io.codearte.nexus-staging")
    signing
}

kotlin {
    js(IR) { library(forNodeJs = false) }
    sourceSets {
        val main by getting {
            dependencies {
                api(asoft("reakt-core", vers.asoft.reakt))
                api(project(":auth-client-core"))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authentication and authorization on the client with kotlin/react"
)
