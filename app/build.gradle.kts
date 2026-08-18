plugins {
    id("com.android.application")
}

android {
    namespace = "com.vidora.ai"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.vidora.ai"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "3.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    
    // Retrofit для работы с API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
