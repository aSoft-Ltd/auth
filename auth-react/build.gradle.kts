plugins {
    kotlin("js")
    id("tz.co.asoft.library")
}

kotlin {
    js(IR) { library() }
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
                api(project(":auth-client"))
            }
        }
    }
}
