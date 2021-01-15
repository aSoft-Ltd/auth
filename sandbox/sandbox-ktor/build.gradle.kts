plugins {
    kotlin("jvm")
    id("tz.co.asoft.applikation")
}

application {
    mainClassName = "tz.co.asoft.MainKt"
}

applikation {
    debug()
    release()
}

kotlin {
    target {
        targetJava("1.8")
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(project(":sandbox-core"))
                implementation(asoft("jwt-hs", vers.asoft.jwt))
                implementation(asoft("applikation-runtime", vers.asoft.builders))
                implementation(project(":authorization-server-ktor"))
            }
        }
    }
}