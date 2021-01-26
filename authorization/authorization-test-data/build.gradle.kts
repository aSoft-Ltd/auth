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
                api(project(":authorization-client-core"))
                api(asoft("persist-inmemory", vers.asoft.persist))
                api(project(":auth-test"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("expect-coroutines", vers.asoft.expect))
                implementation(asoft("test-coroutines", vers.asoft.test))
            }
        }
    }
}
