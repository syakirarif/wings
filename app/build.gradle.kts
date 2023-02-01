import java.util.*

plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

android {

    val properties = File(rootDir, "keystore.properties").inputStream().use {
        Properties().apply { load(it) }
    }
    val mStorePassword = properties.getValue("storePassword") as String
    val mKeyPassword = properties.getValue("keyPassword") as String
    val mKeyAlias = properties.getValue("keyAlias") as String
    val mStoreFile = properties.getValue("storeFile") as String

    signingConfigs {
        create("release") {
            keyAlias = mKeyAlias
            keyPassword = mKeyPassword
            storeFile = file(mStoreFile)
            storePassword = mStorePassword
        }
    }

    compileSdk = BuildAndroidConfig.COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = BuildAndroidConfig.APPLICATION_ID
        minSdk = BuildAndroidConfig.MIN_SDK_VERSION
        targetSdk = BuildAndroidConfig.TARGET_SDK_VERSION
        versionCode = BuildAndroidConfig.VERSION_CODE
        versionName = BuildAndroidConfig.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            proguardFiles("proguard-android-optimize.txt", "proguard-rules.pro")
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeRelease.isTestCoverageEnabled
            signingConfig = signingConfigs.getByName("release")
        }
        getByName(BuildType.DEBUG) {
            applicationIdSuffix = BuildTypeDebug.applicationIdSuffix
            versionNameSuffix = BuildTypeDebug.versionNameSuffix
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
            isTestCoverageEnabled = BuildTypeDebug.isTestCoverageEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        val options = this
        options.jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(project(BuildModules.Commons.CORE))

    // Hilt
//    implementation(Google.dagger.hilt.android)
//    kapt(Google.dagger.hilt.compiler)
//    kapt(Google.dagger.hilt.android.compiler)
    implementation("com.google.dagger:hilt-android:2.43.1")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.4.+")
    implementation("androidx.constraintlayout:constraintlayout:2.1.3")
    implementation("com.balysv:material-ripple:1.0.2")
    kapt("com.google.dagger:hilt-compiler:2.43.1")

}

kapt {
    correctErrorTypes = true
}