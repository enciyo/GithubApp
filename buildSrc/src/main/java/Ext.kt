import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.apply
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependency
import plugins.BasePlugin
import kotlin.reflect.KClass

val BasePlugin.libs: LibrariesForLibs
    get() = target.extensions.getByType<LibrariesForLibs>()

val BasePlugin.applicationExt: ApplicationExtension
    get() = target.extensions.getByType<ApplicationExtension>()

val BasePlugin.librariesExt: LibraryExtension
    get() = target.extensions.getByType<LibraryExtension>()

fun Project.applyPlugins(any: Any) {
    when (any) {
        is String -> pluginManager.apply(any)
        is Class<*> -> pluginManager.apply(any)
        is KClass<*> -> pluginManager.apply(any)
        is Provider<*> -> pluginManager.apply((any.get() as PluginDependency).pluginId)
        else -> throw IllegalStateException("Not handled " + any.javaClass.name)
    }
}

fun DependencyHandlerScope.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}

fun DependencyHandlerScope.kapt(dependencyNotation: Any) {
    add("kapt", dependencyNotation)
}

