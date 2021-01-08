plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

kotlin {
    js(IR) { library() }
    sourceSets {
        val main by getting {
            dependencies {
                api(asoft("reakt-core", vers.asoft.reakt))
                api(project(":auth-client"))
            }
        }
    }
}
