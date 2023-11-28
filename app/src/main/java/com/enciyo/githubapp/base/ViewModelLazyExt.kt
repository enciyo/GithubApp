package com.enciyo.githubapp.base

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlin.reflect.KClass


//Workaround
@MainThread
inline fun <VM : ViewModel> Fragment.viewModels(
    viewModelClass: KClass<VM>,
    noinline ownerProducer: () -> ViewModelStoreOwner = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null,
): Lazy<VM> = createViewModelLazy(
    viewModelClass, { ownerProducer().viewModelStore },
    factoryProducer = factoryProducer ?: {
        (ownerProducer() as? HasDefaultViewModelProviderFactory)?.defaultViewModelProviderFactory
            ?: defaultViewModelProviderFactory
    }
)