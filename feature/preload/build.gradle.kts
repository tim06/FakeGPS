plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.preload"
}

dependencies {
    implementation(project(":core:lifecycle"))
    implementation(project(":core:permission"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)

    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.extensions.coroutines)

    // TODO check, only for Value class
    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.decompose.extensionsCompose)
    implementation(libs.arkivanov.mvi.rx)
}
