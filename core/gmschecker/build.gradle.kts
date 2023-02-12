plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.feature.gmschecker"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.playservices.base)
}