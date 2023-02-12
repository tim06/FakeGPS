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

include(":core:geocoder")
include(":core:lifecycle")
include(":core:gmschecker")
include(":core:permission")
include(":core:fakelocationprovider")

include(":feature:main")
include(":feature:model")
include(":feature:search")
include(":feature:preload")
include(":feature:map:mapmain")
include(":feature:map:common:commonui")
include(":feature:map:common:commondata")
include(":feature:map:provider:google")
include(":feature:map:provider:yandex")
