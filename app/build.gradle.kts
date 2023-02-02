plugins {
    id("fakegps.android.application")
    id("fakegps.android.application.compose")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    defaultConfig {
        applicationId = "com.tim.fakegps"
        versionCode = 1
        versionName = "0.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val debug by getting {
            applicationIdSuffix = ".debug"
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
    implementation(project(":feature:gmschecker"))
    implementation(project(":feature:locationprovider"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3)

    implementation(libs.koin.android.compose)

    implementation(libs.arkivanov.mvi.kotlin)
    implementation(libs.arkivanov.mvi.kotlin.logging)
    implementation(libs.arkivanov.mvi.kotlin.timeTravel)

    implementation(libs.arkivanov.decompose)

    implementation(libs.androidx.dataStore.preferences)

    implementation(libs.yandex.mapkit)
}