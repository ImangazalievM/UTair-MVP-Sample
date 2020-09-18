object Build {

    object Versions {
        const val compileSdk = 29
        const val buildTools = "29.0.3"
        const val minSdk = 18
        const val targetSdk = 29
    }

}

object Dependencies {
    object Versions {
        const val kotlin = "1.4.0"

        const val okhttpVersion = "4.5.0"
    }

    object Tests {
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:1.4.10"
        const val stdJdk = "stdlib-jdk8"

        //unit-tests
        const val junit = "junit:junit:4.13"
        const val rules = "androidx.test:rules:1.3.0"

        private const val spekVersion = "2.0.8"
        const val spek = "org.spekframework.spek2:spek-dsl-jvm:$spekVersion"
        const val spekRunner = "org.spekframework.spek2:spek-runner-junit5:$spekVersion"
        //mocking
        const val mockk = "io.mockk:mockk:1.9.3"
        //assertions
        const val strikt = "io.strikt:strikt-core:0.28.0"
        const val okhttpMockServer = "com.squareup.okhttp3:mockwebserver:${Dependencies.Versions.okhttpVersion}"

        //UI-tests
        const val kakao = "com.agoda.kakao:kakao:2.3.4"
        const val kaspresso = "com.kaspersky.android-components:kaspresso:1.2.0"
    }

}