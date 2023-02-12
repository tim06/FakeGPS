plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.mapmain"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":core:geocoder"))
    implementation(project(":feature:search"))
    implementation(project(":core:gmschecker"))
    implementation(project(":core:fakelocationprovider"))
    implementation(project(":feature:map:common:commonui"))
    implementation(project(":feature:map:common:commondata"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)

    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.kotlinx.coroutines.android)
}