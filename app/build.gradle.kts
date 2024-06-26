@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.nat.winsome_assessment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nat.winsome_assessment"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            buildConfigField ("String", "BASE_URL", "\"https://api.themoviedb.org/3/\"")
            buildConfigField ("String", "API_KEY", "\"559e054278572d3fcc40365ebe979d50\"")
            buildConfigField ("String", "TOKEN", "\"eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NTllMDU0Mjc4NTcyZDNmY2M0MDM2NWViZTk3OWQ1MCIsInN1YiI6IjYwYjQ4Zjg0M2UwOWYzMDA0MDc2N2NjNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.wS5ohtOC6izPKqysV19DAKuGrlNyQfpY3Lzj7u-xHw0\"")
        }
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
    implementation(libs.androidx.lifecycle.runtime.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    //Room
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.androidx.room.ktx)

    //Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    //DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    //Navigation
    implementation(libs.androidx.navigation)
    implementation(libs.kotlinx.serialization.json)


    //Coil
    implementation(libs.coil)
    implementation(libs.coil.gif)
    implementation(libs.coil.compose)

    //Lifecycles
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //For App Theme
    implementation(libs.google.accompanist.pager)
    implementation(libs.google.accompanist.system)

    //Test
    testImplementation(libs.hilt.android.testing)
    kspTest(libs.hilt.android.compiler.v2405)
    testImplementation (libs.kotlinx.coroutines.test)
    // AndroidX Test - Core and Rules
    testImplementation (libs.androidx.core.testing)
    testImplementation (libs.androidx.core)
    testImplementation (libs.androidx.rules)
    // Truth
    testImplementation(libs.truth)


}