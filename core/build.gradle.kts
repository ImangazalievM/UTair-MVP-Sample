import java.io.FileInputStream
import java.util.*

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        minSdkVersion(17)
        targetSdkVersion(29)

        val properties = Properties()
        val propsFile = project.rootProject.file("api_keys.properties")
        properties.load(FileInputStream(propsFile))
        val openWeatherApiKey = properties.getProperty("open_weather_api_key")
        buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"$openWeatherApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildFeatures.viewBinding = true

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    api("androidx.appcompat:appcompat:1.2.0")
    api("com.google.android.material:material:1.2.1")
    api("androidx.recyclerview:recyclerview:1.1.0")
    api("javax.inject:javax.inject:1")

    api("com.github.aartikov.Alligator:alligator:4.0.0")
    api("com.arello-mobile:moxy:1.5.5")
    api("com.arello-mobile:moxy-app-compat:1.5.5")

    api("com.squareup.retrofit2:retrofit:2.9.0")
    api("com.squareup.retrofit2:converter-gson:2.9.0")
    api("com.squareup.okhttp3:logging-interceptor:${Versions.okhttpVersion}")

    api("io.github.inflationx:calligraphy3:3.1.1")
    api("io.github.inflationx:viewpump:2.0.3")

    api("com.wdullaer:materialdatetimepicker:4.2.3")
    api("com.afollestad.material-dialogs:core:3.3.0")
    api("joda-time:joda-time:2.9.9")

    api("com.jakewharton.timber:timber:4.7.1")
}
