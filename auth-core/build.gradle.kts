plugins {
    id("com.android.library")
    kotlin("multiplatform")
    kotlin("plugin.serialization")
    asoft("library")
}

repositories {
    maven(url = "https://kotlin.bintray.com/kotlinx/")
}

kotlin {
    universalLib()
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("org.jetbrains.kotlinx:kotlinx-datetime:${vers.kotlinx.datetime}")
                api(asoft("persist-core", vers.asoft.persist))
                api(asoft("phone-core", vers.asoft.contacts))
                api(asoft("email-core", vers.asoft.contacts))
                api(asoft("name-core", vers.asoft.contacts))
                api(asoft("logging-console", vers.asoft.logging))
            }
        }

        val commonTest by getting {
            dependencies {
                api(asoft("test", vers.asoft.test))
            }
        }
    }
}