plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "group.kalmykov.safe"
    compileSdk = 34

    defaultConfig {
        applicationId = "group.kalmykov.safe"
        minSdk = 30
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
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" /*нижняя панель*/
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    val nav_version = "2.8.3"

    // Jetpack Compose integratio
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Интеграция / Просмотров/фрагментов
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // Поддержка функционального модуля для фрагментов
    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")

    // Тестирование навигации
    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")

    //отсутствие зависимости от степени проверки
    implementation("androidx.navigation:navigation-compose:2.8.3") //Навигация

    implementation("androidx.compose.material:material:1.7.2")//Нижняя навигация

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}