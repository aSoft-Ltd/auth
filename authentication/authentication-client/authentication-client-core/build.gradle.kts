plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    id("tz.co.asoft.library")
}

kotlin {
    universalLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(asoft("viewmodel-core", vers.asoft.viewmodel))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(asoft("kotlinx-serialization-mapper",vers.asoft.mapper))
                api(asoft("either", vers.asoft.either))
                api(project(":authentication-core"))
                api(project(":authorization-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(project(":authentication-inmemory"))
                implementation(asoft("viewmodel-test",vers.asoft.viewmodel))
            }
        }
    }
}
