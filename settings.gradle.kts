pluginManagement {
    repositories {
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.namespace == "com.android") {
                useModule("com.android.tools.build:gradle:${requested.version}")
            }
        }
    }
}

rootProject.name = "auth"

include(":auth-core")
include(":authorization-core")
project(":authorization-core").projectDir = File("authorization/authorization-core")
include(":authentication-core")
project(":authentication-core").projectDir = File("authentication/authentication-core")

include(":authentication-client-core")
project(":authentication-client-core").projectDir = File("authentication/authentication-client/authentication-client-core")

include(":authentication-dao-inmemory")
project(":authentication-dao-inmemory").projectDir = File("authentication/authentication-daos/authentication-dao-inmemory")

include(":authentication-client-react")
project(":authentication-client-react").projectDir =
    File("authentication/authentication-client/authentication-client-browser/authentication-client-react")

include(":sandbox-react")
project(":sandbox-react").projectDir = File("sandbox/sandbox-react")