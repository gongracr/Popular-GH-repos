plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.gongracr.ghreposloader"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.gongracr.ghreposloader"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
        animationsDisabled = true
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Own modules
    api(project(":core"))
    api(project(":network"))
    api(project(":persistence"))

    // Core android libs
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.compose.destinations.core)
    implementation(libs.json.serialization)
    ksp(libs.compose.destinations.ksp)

    // Pagination
    implementation(libs.androidx.paging3)
    implementation(libs.androidx.paging3Compose)

    // Hilt
    implementation(libs.hilt.navigationCompose)
    implementation(libs.hilt.work)
    implementation(libs.hilt.android)
    ksp(libs.ksp.processing.api)
    ksp(libs.ksp.processing.core)
    ksp(libs.hilt.compiler)

    // Image loading
    implementation(libs.coil.compose)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.paging3)
    annotationProcessor(libs.room.compiler)
    ksp(libs.room.compiler)

    // Tests
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.kluent.core)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.paging3.testing)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}