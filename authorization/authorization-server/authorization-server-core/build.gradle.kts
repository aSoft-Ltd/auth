plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    multiplatformLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":authorization-core"))
                api(asoft("jwt-core",vers.asoft.jwt))
                api(project(":rest-server-core"))
//                api(asoft("rest-server-core", vers.asoft.rest))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
            }
        }
    }
}
