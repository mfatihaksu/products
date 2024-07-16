plugins {
    alias(libs.plugins.android.library)
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
    testImplementation(libs.junit.jupiter)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlinx.serialization)
    implementation(libs.bundles.ktor)
    implementation(libs.kotlinx.serialization.core)
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.androidx.room.testing)
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}