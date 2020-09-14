buildscript {
    val kotlinVersion = "1.3.72"

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

extra.apply {
    val rxBindingVersion = "2.2.0"
    set("rxBinding", "com.jakewharton.rxbinding2:rxbinding-kotlin:$rxBindingVersion")
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