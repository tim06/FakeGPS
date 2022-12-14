plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.core.ui"
}

dependencies {
    implementation(libs.androidx.compose.ui.tooling.preview)
}
