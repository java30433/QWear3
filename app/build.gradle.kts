apply("./secret.gradle.kts")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "bakuen.qwear"
    compileSdk = 34

    defaultConfig {
        applicationId = "bakuen.qwear"
        minSdk = 21
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            @Suppress("UNCHECKED_CAST")
            fun <T> ext(key: String) = rootProject.extra[key] as T
            storeFile = ext("storeFile")
            storePassword = ext("storePassword")
            keyAlias = ext("keyAlias")
            keyPassword = ext("keyPassword")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs["release"]
        }
        debug {
            isMinifyEnabled = false
            signingConfig = signingConfigs["release"]
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    //noinspection UseTomlInstead
    implementation("com.tencent.tbs:tbssdk:44286")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(project(":lib-navigator"))
    implementation(project(":lib-protostore"))
    implementation(project(":kritor"))

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.grpc.stub)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.kotlin.stub)
    /*
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    */
}