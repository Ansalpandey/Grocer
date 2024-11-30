plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  id("kotlin-kapt")
  id("com.google.dagger.hilt.android")
  id("org.jetbrains.kotlin.plugin.serialization")
  id("kotlin-parcelize")
}

android {
  namespace = "com.app.humaraapnabazaar"
  compileSdk = 35

  defaultConfig {
    applicationId = "com.app.humaraapnabazaar"
    minSdk = 28
    //noinspection EditedTargetSdkVersion
    targetSdk = 35
    versionCode = 2
    versionName = "1.1"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
  }
  kotlinOptions { jvmTarget = "21" }
  buildFeatures { compose = true }
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
  implementation(libs.androidx.material3.adaptive.navigation.suite.android)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  // Navigation
  implementation("androidx.navigation:navigation-compose:2.8.4")
  implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
  // Hilt
  implementation("com.google.dagger:hilt-android:2.51.1")
  kapt("com.google.dagger:hilt-android-compiler:2.51.1")

  implementation("io.coil-kt:coil-compose:2.4.0") // Latest stable version
  // OkHttp
  implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
  // Coroutines
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
  implementation("androidx.datastore:datastore-preferences:1.1.1")

  implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.7.3")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  implementation("androidx.core:core-splashscreen:1.0.1")
  implementation("androidx.compose.material:material-icons-extended:1.7.5")

  implementation("androidx.compose.material3.adaptive:adaptive:1.0.0")
  implementation("androidx.compose.material3.adaptive:adaptive-layout:1.0.0")
  implementation("androidx.compose.material3.adaptive:adaptive-navigation:1.0.0")

  implementation("androidx.paging:paging-runtime:3.1.1")
  implementation("androidx.paging:paging-compose:1.0.0-alpha17")
  implementation("com.github.a914-gowtham:compose-ratingbar:1.2.3")
}
