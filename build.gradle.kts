buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.hilt.android.gradle.plugin)
        classpath(libs.androidx.navigation.safe.args.gradle.plugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}