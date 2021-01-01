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
                api(asoft("viewmodel-core", vers.asoft.viewmodel))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(asoft("kotlinx-serialization-mapper",vers.asoft.mapper))
                api(asoft("either-core", vers.asoft.duality))
                api(project(":authentication-core"))
                api(project(":authorization-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":authentication-inmemory"))
                implementation(asoft("expect-coroutines",vers.asoft.expect))
                implementation(asoft("viewmodel-test-expect",vers.asoft.viewmodel))
            }
        }
    }
}
