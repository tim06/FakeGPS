plugins {
    id("fakegps.android.library")
}

android {
    namespace = "com.tim.fakegps.feature.map.state"
}

dependencies {
    implementation(libs.playservices.maps)
}
