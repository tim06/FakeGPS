plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.core.di"
}

dependencies {
    implementation(libs.playservices.location)
}
