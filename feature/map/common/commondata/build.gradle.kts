plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.feature.map.common.commondata"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":core:geocoder"))
    implementation(project(":core:gmschecker"))
    implementation(project(":core:fakelocationprovider"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.extensions.coroutines)

    implementation(libs.playservices.maps)

}
