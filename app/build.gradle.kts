plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    id ("androidx.room")
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
    id ("com.google.dagger.hilt.android")
}

android {
    namespace = "com.pomolistapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pomolistapp"
        minSdk = 26
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
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    kapt {
        correctErrorTypes = true
        useBuildCache = true
    }
    room {
        schemaDirectory ("$projectDir/schemas")
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Implementações gerais
    implementation ("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation ("androidx.compose.material:material-icons-extended")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // Hilt
    implementation ("com.google.dagger:hilt-android:2.50")
    kaptTest ("com.google.dagger:hilt-android-compiler:2.50")
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.50")
    kapt ("com.google.dagger:hilt-compiler:2.50")

    // Navigation compose
    implementation ("androidx.navigation:navigation-compose:2.7.3")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Room Dependencies
    implementation ("androidx.room:room-runtime:2.6.0")
    implementation ("androidx.room:room-ktx:2.6.0")
    kapt ("androidx.room:room-compiler:2.6.0")

    // Pickers Dependencies
    implementation ("androidx.compose.material3:material3:1.2.1")

    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    // Worker
    implementation ("androidx.work:work-runtime-ktx:2.8.1")
}