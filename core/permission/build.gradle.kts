plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
    id("fakegps.android.hilt")
}

android {
    namespace = "com.tim.fakegps.core.permission"
}

dependencies {
    implementation(libs.androidx.compose.runtime)
    implementation(libs.accompanist.permissions)
}
