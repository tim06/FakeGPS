plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.core.ds"
}

dependencies {
    implementation(libs.androidx.compose.material3)
}
