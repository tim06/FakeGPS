plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.feature.map.commondata"
}

dependencies {
    implementation(project(":feature:locationprovider"))
    implementation(project(":feature:model"))
    implementation(project(":feature:gmschecker"))

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.extensions.coroutines)

    implementation(libs.playservices.maps)

    // TODO check, only for Value class
    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.mvi.rx)
}
