plugins {
    id("corporate-lib")
}

kotlin.sourceSets {
    val commonMain by getting {
        dependencies {
            api(asoft("klock"))
            api(asoft("persist"))
            api(asoft("phone"))
            api(asoft("email"))
            api(asoft("io"))
            api(asoft("logging-core"))
            api(asoft("tools"))
            api(asoft("task"))
            api(asoft("storage"))
            api(asoft("krypto"))
            api(project(":auth-core"))
            api(project(":authorization-core"))
        }
    }

    val commonTest by getting {
        dependencies {
            api(asoft("test"))
        }
    }
}
