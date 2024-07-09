plugins {
    id("com.android.application")
}

android {
    namespace = "io.github.bryangig.bypassdana"
    compileSdk = 34

    defaultConfig {
        applicationId = "io.github.bryangig.bypassdana"
        minSdk = 24
        targetSdk = 34
        versionCode = 2
        versionName = "2.5"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation("androidx.annotation:annotation-jvm:1.8.0")
    compileOnly("de.robv.android.xposed:api:82")
}