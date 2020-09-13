buildscript {
    val kotlinVersion = "1.3.72"
    extra["kotlin_version"] = kotlinVersion
    
    repositories {
        google()
        jcenter()
    }
    
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
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