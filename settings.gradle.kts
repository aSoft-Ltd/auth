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
// pure auth modules
//include(":auth-core")

// auth-client modules
include(":auth-client-core")
project(":auth-client-core").projectDir = File("auth-client/auth-client-core")
include(":auth-client-react")
project(":auth-client-react").projectDir = File("auth-client/auth-client-react")

// auth-test modules
include(":auth-test")

// pure authorization modules
include(":authorization-core")
project(":authorization-core").projectDir = File("authorization/authorization-core")

// authorization-server modules
include(":authorization-server-core")
project(":authorization-server-core").projectDir = File("authorization/authorization-server/authorization-server-core")
include(":authorization-server-ktor")
project(":authorization-server-ktor").projectDir = File("authorization/authorization-server/authorization-server-ktor")

// authorization-client modules
include(":authorization-client-core")
project(":authorization-client-core").projectDir = File("authorization/authorization-client/authorization-client-core")
include(":authorization-client-react")
project(":authorization-client-react").projectDir = File("authorization/authorization-client/authorization-client-react")

// authorization-dao modules
include(":authorization-dao-rest")
project(":authorization-dao-rest").projectDir = File("authorization/authorization-dao/authorization-dao-rest")


// authorization-test modules
include(":authorization-test-data")
project(":authorization-test-data").projectDir = File("authorization/authorization-test-data")

// pure authentication modules
include(":authentication-core")
project(":authentication-core").projectDir = File("authentication/authentication-core")

// authentication-server modules
include(":authentication-server-core")
project(":authentication-server-core").projectDir = File("authentication/authentication-server/authentication-server-core")
include(":authentication-server-ktor")
project(":authentication-server-ktor").projectDir = File("authentication/authentication-server/authentication-server-ktor")

// authentication client modules
include(":authentication-client-core")
project(":authentication-client-core").projectDir = File("authentication/authentication-client/authentication-client-core")
include(":authentication-client-react")
project(":authentication-client-react").projectDir = File("authentication/authentication-client/authentication-client-react")

// authentication-test modules
include(":authentication-test-data")
project(":authentication-test-data").projectDir = File("authentication/authentication-test-data")

// authentication-dao modules
include(":authentication-dao-inmemory")
project(":authentication-dao-inmemory").projectDir = File("authentication/authentication-dao/authentication-dao-inmemory")
include(":authentication-dao-rest")
project(":authentication-dao-rest").projectDir = File("authentication/authentication-dao/authentication-dao-rest")

// sandbox
include(":sandbox-core")
project(":sandbox-core").projectDir = File("sandbox/sandbox-core")
include(":sandbox-ktor")
project(":sandbox-ktor").projectDir = File("sandbox/sandbox-ktor")
include(":sandbox-react")
project(":sandbox-react").projectDir = File("sandbox/sandbox-react")