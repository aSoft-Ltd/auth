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
                api(asoft("files", vers.asoft.files))
                api(asoft("persist-local", vers.asoft.persist))
                api(asoft("krypto-core", vers.asoft.krypto))
                api(asoft("jwt-core", vers.asoft.jwt))
                api(project(":auth-core"))
            }
        }
    }
}
