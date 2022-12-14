pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FakeGPS"
include(":app")
include(":core:di")
include(":core:ui")
include(":core:ds")
include(":core:extensions")
include(":core:lifecycle")
include(":core:permission")
include(":feature:main")
include(":feature:locationprovider")
include(":feature:gmschecker")
include(":feature:map:google")
include(":feature:map:yandex")
include(":feature:map:state")
