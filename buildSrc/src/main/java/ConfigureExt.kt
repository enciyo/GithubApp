import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

fun ApplicationExtension.configure() = (configureBase() as ApplicationExtension).apply {
    defaultConfig.apply {
        applicationId = DefaultConfig.applicationId
        targetSdk = DefaultConfig.targetSdk
        versionCode = DefaultConfig.versionCode
        versionName = DefaultConfig.versionName
        namespace = DefaultConfig.applicationId
    }
    buildFeatures {
        viewBinding = true
    }
}

fun LibraryExtension.configure(namespace: String) =  configureBase().apply { this.namespace = namespace }

fun CommonExtension<*, *, *, *, *>.configureBase() = apply {
    compileSdk = DefaultConfig.compileSdk
    defaultConfig {
        minSdk = DefaultConfig.minSdk
        testInstrumentationRunner = DefaultConfig.testInstrumentationRunner
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    (this as org.gradle.api.plugins.ExtensionAware).extensions.getByType<KotlinJvmOptions>().jvmTarget = "17"
    buildTypes {
        getByName("release"){
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

}

