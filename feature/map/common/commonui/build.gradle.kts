plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.common.commonui"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":core:geocoder"))
    implementation(project(":feature:map:common:commondata"))
    implementation(project(":feature:map:provider:google"))
    implementation(project(":feature:map:provider:yandex"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
}
