plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.core.permission"
}

dependencies {
    implementation(libs.koin.android)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.accompanist.permissions)
}
