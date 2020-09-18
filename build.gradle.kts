buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Dependencies.Versions.kotlin}")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.2.0.0")
        classpath("org.junit.platform:junit-platform-gradle-plugin:1.0.1")
    }
}

extra.apply {
    val kotlinVersion = "1.3.72"
    set("kotlinVersion", kotlinVersion)
    set("junitPlatform", "1.0.1")
    set("spekVersion", "2.0.0-rc.1")

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