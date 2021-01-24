plugins {
    kotlin("jvm")
    id("tz.co.asoft.library")
}

kotlin {
    target {
        targetJava("1.8")
    }

    sourceSets {
        val main by getting {
            dependencies {
                api(project(":authentication-server-core"))
//                api(asoft("rest-server-ktor", vers.asoft.rest))
                api(project(":rest-server-ktor"))
            }
        }
    }
}