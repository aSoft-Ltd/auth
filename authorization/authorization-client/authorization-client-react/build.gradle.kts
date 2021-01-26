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
                api(asoft("reakt-layouts", vers.asoft.reakt))
                api(asoft("reakt-tables", vers.asoft.reakt))
                api(asoft("reakt-buttons", vers.asoft.reakt))
                api(asoft("reakt-inputs", vers.asoft.reakt))
                api(asoft("reakt-text", vers.asoft.reakt))
                api(asoft("reakt-navigation", vers.asoft.reakt))
                api(asoft("reakt-media", vers.asoft.reakt))
                api(asoft("paging-react", vers.asoft.paging))
                api(asoft("form-react", vers.asoft.form))
                api(asoft("viewmodel-react", vers.asoft.viewmodel))
                api(project(":auth-client-react"))
                api(project(":authorization-client-core"))
            }
        }
    }
}

aSoftOSSLibrary(
    version = vers.asoft.auth,
    description = "A Kotlin Multiplatform library to handle authorization on the client with react"
)
