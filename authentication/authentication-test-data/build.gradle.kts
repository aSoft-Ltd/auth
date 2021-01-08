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
                api(project(":authentication-client-core"))
                api(project(":authentication-inmemory"))
                api(project(":authorization-test-data"))
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
