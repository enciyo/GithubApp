package com.enciyo.data

import com.enciyo.data.local.LocalDataSource
import com.enciyo.data.remote.PER_PAGE
import com.enciyo.data.remote.RemoteDataSource
import com.enciyo.domain.Repository
import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RepositoryImp @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    //Only first page cached
    override fun getUsers(page: Int, username: String): Flow<Result<Users>> = flow {
        remoteDataSource.searchUser(username, page)
            .onSuccess {
                localDataSource.insertUsers(it)
            }
        emitAll(localDataSource.getUsersBy(username, page , PER_PAGE))
    }

    override fun getUsersDetail(username: String) = flow<Result<UserDetail>> {
        remoteDataSource.getUserDetail(username)
            .onSuccess {
                localDataSource.insertUserDetail(it)
            }
        emitAll(localDataSource.getUserDetailById(username))
    }

    override fun getFavorites() = localDataSource.getFavorites()
    override fun favoriteTransactions(user: User, isAdded: Boolean) = flow {
        emit(localDataSource.favoriteTransaction(user, isAdded))
    }

    override fun favoriteTransactions(user: UserDetail, isAdded: Boolean) = flow {
        emit(localDataSource.favoriteTransaction(user, isAdded))
    }
}