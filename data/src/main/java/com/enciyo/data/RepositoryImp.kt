package com.enciyo.data

import com.enciyo.data.local.LocalDataSource
import com.enciyo.data.remote.RemoteDataSource
import com.enciyo.domain.Repository
import com.enciyo.domain.User
import com.enciyo.domain.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    //Only first page cached
    override fun getUsers(page: Int, username: String): Flow<Result<Users>> = flow {
        if (page == 1) {
            emit(localDataSource.getUsers())
        }
        val remote = remoteDataSource.searchUser(username, page)
        emit(remote)
        if (page == 1 && remote.isSuccess) {
            localDataSource.insertUsers(remote.getOrThrow())
        }
    }

    override fun getFavorites() = localDataSource.getFavorites()

    override fun favoriteTransactions(user: User, isAdded: Boolean) = flow {
        emit(localDataSource.favoriteTransaction(user, isAdded))
    }
}