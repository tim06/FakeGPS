plugins {
    id("fakegps.android.application")
    id("fakegps.android.application.compose")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    defaultConfig {
        applicationId = "com.tim.fakegps"
        versionCode = 2
        versionName = "0.0.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            isDebuggable = true
            isMinifyEnabled = false
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    packagingOptions {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    namespace = "com.tim.fakegps"
}

dependencies {
    implementation(project(":feature:main"))
    implementation(project(":core:permission"))
    implementation(project(":core:gmschecker"))
    implementation(project(":core:fakelocationprovider"))
    implementation(project(":core:geocoder"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)

    implementation(libs.koin.android.compose)

    implementation(libs.arkivanov.decompose)
    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.logging)
    implementation(libs.arkivanov.mvi.kotlin.timeTravel)

    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.yandex.mapkit)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)

    androidTestImplementation(project(":feature:map:common:commonui"))
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.testManifest)
}