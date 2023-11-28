package com.enciyo.githubapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enciyo.domain.Repository
import com.enciyo.domain.SearchUserUseCase
import com.enciyo.domain.User
import com.enciyo.githubapp.R
import com.enciyo.githubapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchUserUseCase: SearchUserUseCase,
    private val repository: Repository,
) : BaseViewModel() {

    private val _state = MutableLiveData(HomeUiState())
    val state get() = _state as LiveData<HomeUiState>

    private var vmState = ViewModelState()

    private fun getUsers(page: Int, username: String) {
        searchUserUseCase.invoke(page, username)
            .handle {
                val newUsers = if (page == 1) it.users else it.users + _state.value!!.users
                _state.value =
                    _state.value!!.copy(
                        users = newUsers,
                        isShowFavorites = it.users.isEmpty(),
                        searchKeyword = username
                    )
                vmState =
                    vmState.copy(nextPage = it.nextPage, page = it.currentPage, username = username)
            }
    }


    fun init() {
        vmState = ViewModelState()
        _state.value =
            _state.value!!.copy(users = listOf(), isShowFavorites = true, searchKeyword = "")
        checkFavorites()
    }

    fun search(username: String) {
        vmState = ViewModelState(username = username)
        getUsers(1, username)
    }

    fun loadMore() {
        val nextPage = vmState.nextPage
        if (nextPage != null) {
            getUsers(nextPage, vmState.username)
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

    fun checkFavorites() {
        repository.getFavorites()
            .handle {
                _state.value = _state.value!!.copy(favorites = it.users)
            }
    }

    data class HomeUiState(
        val users: List<User> = listOf(),
        val favorites: List<User> = listOf(),
        val isShowFavorites: Boolean = true,
        val searchKeyword: String = ""
    ) {

        fun getTitle(context: Context) =
            if (isShowFavorites) context.getString(R.string.favorites) else context.getString(R.string.recent_user_searches)

        fun getNavigationIcon() =
            if (searchKeyword.isNotEmpty()) R.drawable.vc_arrow_back else R.drawable.vc_search
    }


    private data class ViewModelState(
        val nextPage: Int? = null,
        val page: Int = 1,
        val username: String = ""
    )

}