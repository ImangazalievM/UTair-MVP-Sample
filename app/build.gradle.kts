import java.io.FileInputStream
import java.util.*

val kotlin_version = findProperty("kotlin_version")

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

android {
    compileSdkVersion(29)
    buildToolsVersion("29.0.3")

    defaultConfig {
        applicationId = "com.utair"
        minSdkVersion(17)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"

        val properties = Properties()
        val propsFile = project.rootProject.file("local.properties")
        properties.load(FileInputStream(propsFile))
        val openWeatherApiKey = properties.getProperty("OPENWEATHER_API_KEY")
        buildConfigField("String", "OPENWEATHER_API_KEY", "\"$openWeatherApiKey\"")

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

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
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("com.google.android.material:material:1.2.1")
    implementation("androidx.recyclerview:recyclerview:1.1.0")

    kapt("com.google.dagger:dagger-compiler:2.29.1")
    implementation("com.google.dagger:dagger:2.29.1")

    implementation("com.github.aartikov.Alligator:alligator:4.0.0")
    implementation("com.arello-mobile:moxy:1.5.5")
    implementation("com.arello-mobile:moxy-app-compat:1.5.5")
    kapt("com.arello-mobile:moxy-compiler:1.5.5")

    implementation("io.reactivex.rxjava2:rxjava:2.2.2")
    implementation("io.reactivex.rxjava2:rxandroid:2.1.0")
    implementation("io.reactivex.rxjava2:rxkotlin:2.0.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:adapter-rxjava2:2.3.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("io.github.inflationx:calligraphy3:3.1.1")
    implementation("io.github.inflationx:viewpump:2.0.3")

    implementation("com.wdullaer:materialdatetimepicker:4.2.3")
    implementation("com.afollestad.material-dialogs:core:3.3.0")
    implementation("joda-time:joda-time:2.9.9")
}
