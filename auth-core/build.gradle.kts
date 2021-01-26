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
                api("org.jetbrains.kotlinx:kotlinx-datetime:${vers.kotlinx.datetime}")
                api(asoft("persist-core", vers.asoft.persist))
                api(asoft("phone-core", vers.asoft.contacts))
                api(asoft("email-core", vers.asoft.contacts))
                api(asoft("name-core", vers.asoft.contacts))
                api(asoft("result-core", vers.asoft.duality))
                api(asoft("logging-console", vers.asoft.logging))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
            }
        }
    }
}