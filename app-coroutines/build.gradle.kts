import de.mannodermaus.gradle.plugins.junit5.junitPlatform

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("de.mannodermaus.android-junit5")
}

android {
    compileSdkVersion(Build.Versions.compileSdk)
    buildToolsVersion(Build.Versions.buildTools)

    defaultConfig {
        applicationId = "com.utair"
        minSdkVersion(Build.Versions.minSdk)
        targetSdkVersion(Build.Versions.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
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
    val kotlinCoroutineVersion = "1.3.3"
    val toothpickVersion = "2.0.0"

    api(project(":core"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$kotlinCoroutineVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutineVersion")

    kapt("com.arello-mobile:moxy-compiler:1.5.5")
    implementation("com.github.stephanenicolas.toothpick:toothpick:$toothpickVersion")
    implementation("com.github.stephanenicolas.toothpick:toothpick-runtime:$toothpickVersion")
    kapt("com.github.stephanenicolas.toothpick:toothpick-compiler:$toothpickVersion")

    // unit-tests
    testImplementation(Dependencies.Tests.kotlinReflect)
    testImplementation(kotlin(Dependencies.Tests.stdJdk))

    testImplementation(Dependencies.Tests.spek)
    testImplementation(Dependencies.Tests.spekRunner)
    testImplementation(Dependencies.Tests.mockk)
    testImplementation(Dependencies.Tests.strikt)

    testImplementation(Dependencies.Tests.okhttpMockServer)

    androidTestImplementation(Dependencies.Tests.junit)
    androidTestImplementation(Dependencies.Tests.rules)
    androidTestImplementation(Dependencies.Tests.kakao)
    androidTestImplementation(Dependencies.Tests.kaspresso)
}
