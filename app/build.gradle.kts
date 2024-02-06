plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.pandacorp.lechat"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.pandacorp.lechat"
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
    // Fork of github.com/aallam/openai-kotlin that works with the TogetherAI API
    implementation("io.ktor:ktor-client-okhttp:2.3.7")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.github.skydoves:powerspinner:1.2.7")

    // Koin
    implementation("io.insert-koin:koin-android:3.5.3")

    // Room Database
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")

    implementation(project(":openai-kotlin"))
}