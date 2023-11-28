package com.enciyo.githubapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.enciyo.domain.Repository
import com.enciyo.domain.model.User
import com.enciyo.githubapp.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: Repository,
) : BaseViewModel(){

    private val _state = MutableLiveData(SearchUiState())
    val state get() = _state as LiveData<SearchUiState>

    private var vmState = ViewModelState()

    private var searchJob: Job? = null

    private fun getUsers(page: Int, username: String) {
        searchJob?.cancel()
        searchJob = repository.getUsers(page, username)
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


    fun loadMore() {
        val nextPage = vmState.nextPage
        if (nextPage != null) {
            getUsers(nextPage, vmState.username)
        }
    }

    fun search(keyword: String) {
        vmState = ViewModelState(username = keyword)
        getUsers(1, keyword)
    }


    private fun stateUpdate(block: (exists: SearchUiState) -> SearchUiState) {
        _state.value = block.invoke(currentState())
    }

    private fun currentState() = _state.value!!


    data class SearchUiState(
        val users: List<User> = listOf(),
        val histories: List<User> = listOf(),
        val isShowFavorites: Boolean = true,
        val searchKeyword: String = ""
    )

    private data class ViewModelState(
        val nextPage: Int? = null,
        val page: Int = 0,
        val username: String = ""
    )
}