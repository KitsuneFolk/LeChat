plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.pandacorp.togetheraichat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pandacorp.togetheraichat"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    buildFeatures {
        viewBinding = true
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
    // Android
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.preference:preference-ktx:1.2.1")

    // Custom
    implementation("com.github.MrRuslanYT:SplashScreen:1.0.1")
    implementation("com.fragula2:fragula-core:2.10.1")
    // Fork of github.com/aallam/openai-kotlin with fixed a few properties not being null by default
    implementation("com.github.MrRuslanYT.openai-kotlin:openai-client:db57b563b4")
    implementation("io.ktor:ktor-client-okhttp:2.3.7")

    // Koin
    implementation("io.insert-koin:koin-android:3.5.3")
}