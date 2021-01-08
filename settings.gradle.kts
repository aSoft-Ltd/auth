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
include(":authorization-client-core")
project(":authorization-client-core").projectDir = File("authorization/authorization-client/authorization-client-core")
include(":authorization-client-react")
project(":authorization-client-react").projectDir = File("authorization/authorization-client/authorization-client-react")

include(":authorization-test-data")
project(":authorization-test-data").projectDir = File("authorization/authorization-test-data")

include(":authentication-core")
project(":authentication-core").projectDir = File("authentication/authentication-core")

include(":authentication-test-data")
project(":authentication-test-data").projectDir = File("authentication/authentication-test-data")

include(":authentication-client-core")
project(":authentication-client-core").projectDir = File("authentication/authentication-client/authentication-client-core")

include(":authentication-client-react")
project(":authentication-client-react").projectDir = File("authentication/authentication-client/authentication-client-react")

include(":authentication-inmemory")
project(":authentication-inmemory").projectDir = File("authentication/authentication-inmemory")

include(":auth-client")

include(":auth-test")

include(":auth-react")
//project(":auth-react").projectDir = File("auth-react")

include(":authorization-react")
project(":authorization-react").projectDir = File("authorization/authorization-react")

include(":sandbox-react")
project(":sandbox-react").projectDir = File("sandbox/sandbox-react")