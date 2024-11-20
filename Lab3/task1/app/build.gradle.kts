plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.sharedpreferencesdemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.sharedpreferencesdemo"
        minSdk = 24
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
}

dependencies {
    implementation("androidx.core:core-ktx:1.10.1") // Stable older version
    implementation("androidx.appcompat:appcompat:1.6.1") // Common stable version
    implementation("com.google.android.material:material:1.8.0") // Stable older version
    implementation("androidx.constraintlayout:constraintlayout:2.1.4") // Stable older version
    testImplementation("junit:junit:4.13.2") // No need to change; it's stable
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // Matches common stable AndroidX versions
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1") // Matches common stable AndroidX versions
}
