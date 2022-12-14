plugins {
    id("fakegps.android.library")
    id("fakegps.android.hilt")
}

android {
    namespace = "com.tim.fakegps.feature.gmschecker"
}

dependencies {
    implementation(libs.playservices.base)
}