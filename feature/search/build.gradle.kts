plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.search"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":core:geocoder"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)

    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.extensions.coroutines)
}