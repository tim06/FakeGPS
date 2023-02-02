plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.google"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":feature:map:commondata"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.google.maps)
    implementation(libs.playservices.maps)
}
