plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tim.fakegps.feature.main"
}

dependencies {
    implementation(project(":core:permission"))
    implementation(project(":feature:preload"))
    implementation(project(":core:gmschecker"))
    implementation(project(":core:geocoder"))
    implementation(project(":feature:map:mapmain"))
    implementation(project(":core:fakelocationprovider"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.decompose.extensionsCompose)
}
