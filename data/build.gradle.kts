plugins {
    alias(libs.plugins.android.library)
    //alias(libs.plugins.kotlin.android.ksp)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}
android {
    compileSdk = 34
    namespace = "com.mfa.products.data"
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.kotlin.stb)
    implementation(libs.androidx.core.ktx)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.core)
}