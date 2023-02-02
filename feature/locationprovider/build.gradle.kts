plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.feature.locationProvider"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
}