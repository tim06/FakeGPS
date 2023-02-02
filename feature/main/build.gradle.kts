plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
    id("kotlin-parcelize")
}

android {
    namespace = "com.tim.fakegps.feature.main"
}

dependencies {
    implementation(project(":core:lifecycle"))
    implementation(project(":core:permission"))
    implementation(project(":feature:locationprovider"))
    implementation(project(":feature:gmschecker"))
    implementation(project(":feature:preload"))
    implementation(project(":feature:map:commondata"))
    implementation(project(":feature:map:commonui"))
    implementation(project(":feature:map:google"))
    implementation(project(":feature:map:yandex"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtimeCompose)

    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.decompose.extensionsCompose)
}
