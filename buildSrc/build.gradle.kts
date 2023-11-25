import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.android.gradlePlugin)
    implementation(libs.build.logic.kotlin.gradlePlugin)
    gradleApi()
}


gradlePlugin{
    plugins {
        register("android-plugin"){
            id = "android-plugin"
            implementationClass = "plugins.AndroidAppPlugin"
        }
    }
}