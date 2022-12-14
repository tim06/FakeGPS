import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("fakegps.android.application")
    id("fakegps.android.application.compose")
    id("fakegps.android.hilt")
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
        val googleMapApiKey = gradleLocalProperties(rootDir).getProperty("GOOGLE_MAPS_API_KEY")
        val debug by getting {
            applicationIdSuffix = ".debug"
            buildConfigField("String", "GOOGLE_MAPS_API_KEY", googleMapApiKey)
            buildConfigField("String", "YANDEX_MAPS_API_KEY", gradleLocalProperties(rootDir).getProperty("YANDEX_MAPS_API_KEY"))
            addManifestPlaceholders(mapOf("GOOGLE_MAPS_API_KEY" to googleMapApiKey))
        }
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")

            buildConfigField("String", "GOOGLE_MAPS_API_KEY", googleMapApiKey)
            buildConfigField("String", "YANDEX_MAPS_API_KEY", gradleLocalProperties(rootDir).getProperty("YANDEX_MAPS_API_KEY"))
            addManifestPlaceholders(mapOf("GOOGLE_MAPS_API_KEY" to googleMapApiKey))
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
    implementation(project(":core:di"))
    implementation(project(":core:ds"))
    implementation(project(":feature:main"))
    implementation(project(":feature:gmschecker"))
    implementation(project(":feature:map:state"))
    implementation(project(":feature:map:google"))
    implementation(project(":feature:map:yandex"))

    implementation(libs.playservices.location)
    implementation(libs.playservices.maps)
    implementation(libs.google.maps)
    implementation(libs.google.maps.widgets)

    implementation(libs.accompanist.permissions)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.lifecycle.runtimeCompose)
    implementation(libs.androidx.compose.runtime.tracing)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
}