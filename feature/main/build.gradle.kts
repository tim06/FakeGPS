plugins {
    id("fakegps.android.library")
    id("fakegps.android.library.compose")
    id("fakegps.android.hilt")
}

android {
    namespace = "com.tim.fakegps.feature.main"
}

dependencies {
    implementation(project(":core:ds"))
    implementation(project(":core:lifecycle"))
    implementation(project(":core:extensions"))
    implementation(project(":core:permission"))
    implementation(project(":feature:locationprovider"))
    implementation(project(":feature:gmschecker"))
    implementation(project(":feature:map:state"))
    implementation(project(":feature:map:google"))
    implementation(project(":feature:map:yandex"))

    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.playservices.location)
    implementation(libs.playservices.maps)
}
