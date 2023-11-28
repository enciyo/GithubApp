package com.enciyo.githubapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enciyo.domain.Repository
import com.enciyo.domain.SearchUserUseCase
import com.enciyo.domain.User
import com.enciyo.githubapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val repository: Repository,
) : BaseViewModel() {

    private val _state = MutableLiveData<HomeUiState>()
    val state get() = _state as LiveData<HomeUiState>

    private var vmState = ViewModelState()

    private fun getUsers(page: Int, username: String) {
        searchUserUseCase.invoke(page, username)
            .handle {
                _state.value = HomeUiState(it.users)
                vmState =
                    vmState.copy(nextPage = it.nextPage, page = it.currentPage, username = username)
            }
    }


    fun search(username: String) {
        getUsers(1, username)
    }

    fun loadMore() {
        val nextPage = vmState.nextPage
        val username = vmState.username
        if (nextPage != null && username.isNotEmpty()) {
            getUsers(nextPage, username)
        }
    }

    fun addFavorite(user: User) {
        repository.favoriteTransactions(user, true)
            .handle { }
    }

    fun removeFavorite(user: User) {
        repository.favoriteTransactions(user, false)
            .handle { }
    }

    data class HomeUiState(val users: List<User>)
    private data class ViewModelState(
        val nextPage: Int? = null,
        val page: Int = 1,
        val username: String = ""
    )

}