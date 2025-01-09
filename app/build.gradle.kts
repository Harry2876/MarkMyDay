plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

        kotlin("kapt")
}

android {
    namespace = "com.example.markmyday"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.markmyday"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation (libs.androidx.ui)
    implementation ("androidx.compose.material:material:1.7.6")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.7.6")
    implementation ("androidx.activity:activity-compose:1.9.3")
    implementation ("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.6.1")
    implementation ("androidx.core:core-ktx:1.15.0")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.8.7")
    implementation ("androidx.work:work-runtime-ktx:2.10.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}