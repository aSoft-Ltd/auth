plugins {
    id("corporate-lib-jvm")
}

dependencies {
    api(project(":authorization-core"))
    api(project(":rest-api-ktor"))
}
