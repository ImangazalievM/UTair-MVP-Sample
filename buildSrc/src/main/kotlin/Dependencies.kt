object Versions {
    const val kotlin = "1.3.72"

    const val okhttpVersion = "4.5.0"
}

object Dependencies {

    object Tests {
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:1.4.10"
        const val stdJdk = "stdlib-jdk8"

        //tests runner
        private const val spekVersion = "2.0.8"
        const val spek = "org.spekframework.spek2:spek-dsl-jvm:$spekVersion"
        const val spekRunner = "org.spekframework.spek2:spek-runner-junit5:$spekVersion"

        //mocking
        const val mockk = "io.mockk:mockk:1.9.3"

        //assertions
        const val strikt = "io.strikt:strikt-core:0.28.0"

        const val okhttpMockServer = "com.squareup.okhttp3:mockwebserver:${Versions.okhttpVersion}"
    }

}