plugins {
    id("fakegps.android.library")
    id("fakegps.android.hilt")
}

android {
    namespace = "com.tim.fakegps.core.di"
}

dependencies {
    implementation(libs.playservices.location)
}
