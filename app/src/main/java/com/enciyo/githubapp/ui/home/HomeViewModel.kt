package com.enciyo.githubapp.ui.home

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enciyo.domain.Repository
import com.enciyo.domain.applyFavorites
import com.enciyo.domain.model.User
import com.enciyo.domain.usecases.SearchUserUseCase
import com.enciyo.githubapp.R
import com.enciyo.githubapp.base.BaseViewModel
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
            .handle { remote ->
                stateUpdate { exists ->
                    exists.copy(
                        users = if (page == 1) remote.users else remote.users + currentState().users,
                        isShowFavorites = remote.users.isEmpty(),
                        searchKeyword = username
                    )
                }

                vmState =
                    vmState.copy(
                        nextPage = remote.nextPage,
                        page = remote.currentPage,
                        username = username
                    )
            }
    }

    init {
        getFavorites()
    }

    fun init() {
        vmState = ViewModelState()
        stateUpdate { it.copy(users = listOf(), isShowFavorites = true, searchKeyword = "") }
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

    private fun getFavorites() {
        repository.getFavorites()
            .handle {local->
                val usersWithFavorites = currentState().users.applyFavorites(local.users)
                stateUpdate {
                    it.copy(users = usersWithFavorites, favorites = local.users)
                }
            }
    }

    private fun stateUpdate(block: (exists: HomeUiState) -> HomeUiState) {
        _state.value = block.invoke(currentState())
    }

    private fun currentState() = _state.value!!

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
        val page: Int = 0,
        val username: String = ""
    )
}

