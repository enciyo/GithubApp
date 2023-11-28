package com.enciyo.githubapp.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlin.reflect.KClass


abstract class BaseFragment<V : ViewBinding, VM : BaseViewModel>(
    @LayoutRes layoutRes: Int,
    vbFactory: (View) -> V,
    vmClass: KClass<VM>,
) : Fragment(layoutRes) {

    protected val binding by viewBinding(vbFactory, onViewDestroyed = ::onClearReferences)
    protected val vm by viewModels(vmClass)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    protected open fun onClearReferences(safeBinding: V) = Unit


    fun NavDirections.navigate() {
        findNavController().navigate(this)
    }
}
