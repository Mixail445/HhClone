plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}

android {
    namespace = "com.example.hhclone"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.hhclone"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        viewBinding = true
    }
    kapt {
        arguments {
        }
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":favorites"))
    implementation(project(":profile"))
    implementation(project(":responses"))
    implementation(project(":search"))
    implementation(project(":message"))
    implementation(project(":logIn"))
    implementation(libs.retrofit)
    kapt(libs.dagger.compiler)
    implementation(libs.dagger)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.moshi)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin.codegen)
}
