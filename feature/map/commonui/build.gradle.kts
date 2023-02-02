plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
}

android {
    namespace = "com.tim.fakegps.feature.map.commonui"
}

dependencies {
    implementation(project(":feature:model"))
    implementation(project(":feature:map:commondata"))
    implementation(project(":feature:map:google"))
    implementation(project(":feature:map:yandex"))

    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)

    // TODO check, only for Value class
    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.decompose.extensionsCompose)
}
