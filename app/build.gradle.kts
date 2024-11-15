plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp") version "2.0.21-1.0.25"
}

android {
    namespace = "group.kalmykov.safe"
    compileSdk = 35

    defaultConfig {
        applicationId = "group.kalmykov.safe"
        minSdk = 30
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    val room_version = "2.6.1"

    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // To use Kotlin annotation processing tool (kapt)
   // kapt("androidx.room:room-compiler:$room_version")
    // To use Kotlin Symbol Processing (KSP)
    ksp("androidx.room:room-compiler:$room_version")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")

    // optional - RxJava2 support for Room
  //  implementation("androidx.room:room-rxjava2:$room_version")

    // optional - RxJava3 support for Room
   // implementation("androidx.room:room-rxjava3:$room_version")

    // optional - Guava support for Room, including Optional and ListenableFuture
    implementation("androidx.room:room-guava:$room_version")

    // optional - Test helpers
    //testImplementation("androidx.room:room-testing:$room_version")

   implementation(libs.androidx.biometric) //Биометрия
    implementation(libs.androidx.security.crypto)

    implementation(libs.kotlinx.serialization.json)

    // Jetpack Compose integratio
    implementation(libs.navigation.compose)

    // Интеграция / Просмотров/фрагментов
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.navigation.ui)

    // Поддержка функционального модуля для фрагментов
    implementation(libs.androidx.navigation.dynamic.features.fragment)


    //отсутствие зависимости от степени проверки
    implementation(libs.navigation.compose) //Навигация

    implementation(libs.androidx.material)//Нижняя навигация

    implementation(libs.androidx.lifecycle.viewmodel.compose)

    implementation(libs.androidx.datastore)
    implementation(libs.androidx.datastore.core) //Хранилище

    implementation(libs.datastore.v100)
    implementation(libs.protobuf.javalite)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(kotlin("reflect"))




    // Тестирование навигации
    androidTestImplementation(libs.androidx.navigation.testing)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

}

