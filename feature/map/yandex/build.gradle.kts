plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.yandex"
}

dependencies {
    implementation(project(":core:lifecycle"))
    implementation(project(":feature:map:state"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)

    api(libs.yandex.mapkit)
}
