plugins {
    kotlin("plugin.serialization") version vers.kotlin apply false
    id("tz.co.asoft.library") version vers.asoft.builders apply false
    id("tz.co.asoft.applikation") version vers.asoft.builders apply false
}

subprojects {
    group = "tz.co.asoft"
    version = vers.asoft.auth
    repositories {
        maven("https://maven.pkg.jetbrains.space/asofttz/p/libs/maven") {
            credentials {
                username = System.getenv("SPACE_USERNAME") ?: System.getenv("JB_SPACE_CLIENT_ID")
                password = System.getenv("SPACE_PASSWORD") ?: System.getenv("JB_SPACE_CLIENT_SECRET")
            }
        }
    }
}