plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.yandex"
}

dependencies {
    implementation(project(":core:lifecycle"))
    implementation(project(":feature:model"))
    implementation(project(":feature:map:commondata"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)

    implementation(libs.yandex.mapkit)
}
