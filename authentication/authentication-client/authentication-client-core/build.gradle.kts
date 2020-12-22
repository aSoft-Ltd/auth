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
                api(asoft("either", vers.asoft.either))
                api(project(":authentication-core"))
                api(project(":authorization-core"))
            }
        }
    }
}