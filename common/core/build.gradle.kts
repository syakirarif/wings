plugins {
    id("com.android.library")
    id("dagger.hilt.android.plugin")
    kotlin("android")
    kotlin("kapt")
}

apply(from = "$rootDir/buildSrc/android-config-module.gradle")

dependencies {
    api(AndroidX.core.ktx)
    api(AndroidX.fragment.ktx)
    api(AndroidX.paging.runtimeKtx)
    api(AndroidX.navigation.uiKtx)
    api(AndroidX.navigation.fragmentKtx)
    api(AndroidX.navigation.common)

    api(AndroidX.lifecycle.liveDataKtx)
    api(AndroidX.lifecycle.viewModelKtx)
    api(KotlinX.coroutines.android)

    api(Google.android.play.core)

    // Retrofit
    api(Square.retrofit2.retrofit)
    api(Square.retrofit2.converter.gson)
    api(Square.okHttp3.loggingInterceptor)

    // Room
    kapt(AndroidX.room.compiler)
    annotationProcessor(AndroidX.room.compiler)

    // Hawk
    api("com.orhanobut:hawk:2.0.1")

    // Hilt
//    api(Google.dagger.hilt.android)
//    kapt(Google.dagger.hilt.compiler)

    implementation("com.google.dagger:hilt-android:2.43.1")
    kapt("com.google.dagger:hilt-compiler:2.43.1")

    // Timber
    api(JakeWharton.timber)

    // Coil
    api(COIL)

    // Kiel
    api("io.github.ibrahimyilmaz:kiel:1.2.1")

    // Hawk
    implementation("com.orhanobut:hawk:2.0.1")
}