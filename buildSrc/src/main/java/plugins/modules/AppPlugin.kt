package plugins.modules

import libs
import configure
import applicationExt
import implementationProject
import implementation
import plugins.common.DaggerHiltPlugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import plugins.BasePlugin


class AppPlugin : BasePlugin() {

    override val pluginList: List<Any>
        get() = listOf(
            libs.plugins.com.android.application,
            libs.plugins.org.jetbrains.kotlin.android,
            libs.plugins.navigation.safeargs.kotlin,
            DaggerHiltPlugin::class.java,
            libs.plugins.kotlin.kapt
        )

    override val implementations: List<Any>
        get() = listOf(
            libs.core.ktx,
            libs.appcompat,
            libs.material,
            libs.constraintlayout,
            libs.androidx.navigation.fragment.ktx,
            libs.androidx.navigation.ui.ktx,
            libs.lifecycle.livedata.ktx,
            libs.lifecycle.viewmodel.ktx,
            libs.viewbindingpropertydelegate.full,
            libs.androidx.core.splashscreen,
            libs.glide,
            libs.androidx.recyclerview,
            libs.androidx.lifecycle.viewmodel.savedstate
        )


    override fun beforeApply(target: Project) {
        super.beforeApply(target)
        applicationExt.configure()
    }

    override fun afterApply(target: Project) {
        implementationProject(":data") // Not correct by Uncle Bob Arch but need for compilers
        implementationProject(":domain")
        implementationProject(":shared")
    }

}