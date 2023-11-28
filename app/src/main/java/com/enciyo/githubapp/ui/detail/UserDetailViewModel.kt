package com.enciyo.githubapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.enciyo.domain.Repository
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.usecases.UserDetailUseCase
import com.enciyo.githubapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userDetailUseCase: UserDetailUseCase,
    private val repository: Repository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {


    private var args = UserDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    private val _detail = MutableLiveData<UserDetail>()
    val detail get() = _detail as LiveData<UserDetail>


    init {
        getDetail()
        checkFavorites()
    }

    private fun getDetail() {
        userDetailUseCase.invoke(args.username)
            .handle {
                _detail.value = it
            }
    }

    private fun checkFavorites() {
        repository.getFavorites()
            .handle {
                val detail = _detail.value
                if (detail != null) {
                    _detail.value =
                        _detail.value!!.copy(isFavorite = it.users.find { it.id == detail.id } != null)
                }
            }
    }

    fun deleteFavorite(detail: UserDetail) {
        repository.favoriteTransactions(detail, false)
            .handle { }

    }

    fun addFavorite(detail: UserDetail) {
        repository.favoriteTransactions(detail, true)
            .handle { }
    }

}