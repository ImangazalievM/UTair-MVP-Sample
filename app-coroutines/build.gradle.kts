import de.mannodermaus.gradle.plugins.junit5.junitPlatform

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
    id("de.mannodermaus.android-junit5")
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

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    testOptions {
        junitPlatform {
            filters {
                includeEngines("spek2")
            }
        }
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
}
