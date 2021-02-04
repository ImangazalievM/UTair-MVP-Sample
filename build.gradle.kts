buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Versions.kotlin}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.2.0.0")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://jitpack.io") }
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}