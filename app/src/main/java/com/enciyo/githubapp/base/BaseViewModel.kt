package com.enciyo.githubapp.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading = _loading as LiveData<Boolean>


    fun <I> Flow<Result<I>>.handle(
        isShowLoading: Boolean = true,
        isShowError: Boolean = false,
        consumer: suspend (I) -> Unit,
    ): Job {
        return this
            .onEach {
                when {
                    it.isFailure -> {
                        Log.i("MyLogger", it.exceptionOrNull()?.message.orEmpty())
                    }
                    it.isSuccess -> {
                        consumer.invoke(it.getOrThrow())
                    }
                }

            }
            .onEach {
                if (isShowLoading) _loading.value = false
            }
            .onStart {
                if (isShowLoading) _loading.value = true
            }
            .onCompletion {
                if (isShowLoading) _loading.value = false
            }
            .launchIn(viewModelScope)
    }



}