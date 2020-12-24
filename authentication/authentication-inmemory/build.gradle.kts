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
                api(project(":authentication-client-core"))
                api(asoft("jwt-hs", vers.asoft.jwt))
                api(asoft("persist-inmemory", vers.asoft.persist))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test",vers.asoft.test))
            }
        }
    }
}
