plugins {
    kotlin("js")
    id("tz.co.asoft.applikation")
}

group = "tz.co.asoft.auth"
version = vers.asoft.auth

applikation {
    debug()
    release()
}

kotlin {
    js(IR) { application() }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(asoft("applikation-runtime", vers.asoft.builders))
                implementation(project(":sandbox-core"))
                implementation(project(":authentication-test-data"))
                implementation(project(":authorization-test-data"))
                implementation(project(":authentication-client-react"))
                implementation(project(":authorization-client-react"))
            }
        }

        val test by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
            }
        }
    }
}
