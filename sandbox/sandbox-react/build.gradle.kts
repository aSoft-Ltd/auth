import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer

plugins {
    kotlin("js")
    id("tz.co.asoft.applikation")
}

group = "tz.co.asoft.auth"
version = vers.asoft.auth

applikation {
    debug()
    release()
}

kotlin {
    js(IR) {
        compilations.all {
            kotlinOptions.metaInfo = false
        }
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
                devServer = DevServer(
                    open = false,
                    contentBase = listOf(file("build/processedResources/js/main").absolutePath)
                )
            }
        }
        binaries.executable()
    }

    sourceSets {
        val main by getting {
            dependencies {
                implementation(asoft("applikation-runtime", vers.asoft.builders))
                implementation(project(":authentication-inmemory"))
                implementation(project(":auth-react"))
                implementation(asoft("jwt-hs", vers.asoft.jwt))
                implementation(devNpm("file-loader", "*"))
            }
        }
    }
}

