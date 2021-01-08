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
                api(asoft("krypto-core", vers.asoft.krypto))
                api(asoft("viewmodel-core", vers.asoft.viewmodel))
                api(project(":authorization-core"))
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(asoft("test-core", vers.asoft.test))
                implementation(project(":authorization-test-data"))
                implementation(asoft("expect-coroutines", vers.asoft.expect))
                implementation(asoft("viewmodel-test-expect", vers.asoft.viewmodel))
            }
        }
    }
}
