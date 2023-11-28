package com.enciyo.data.local

import android.util.Log
import com.enciyo.data.local.dao.FavoriteDao
import com.enciyo.data.local.dao.UserDao
import com.enciyo.data.local.dao.UserDetailDao
import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserDetailEntity
import com.enciyo.data.local.entity.UsersEntity
import com.enciyo.data.local.mapper.toFavoriteEntity
import com.enciyo.data.local.mapper.toUser
import com.enciyo.data.local.mapper.toUserDetailEntity
import com.enciyo.data.local.mapper.toUserEntity
import com.enciyo.data.remote.PER_PAGE
import com.enciyo.domain.model.User
import com.enciyo.domain.model.UserDetail
import com.enciyo.domain.model.Users
import com.enciyo.shared.GitDispatchers
import com.enciyo.shared.annotation.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.withContext
import javax.inject.Inject

//Sources may separate by User, Detail vs.
class LocalDataSourceImp @Inject constructor(
    @Dispatcher(GitDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val userDao: UserDao,
    private val favorite: FavoriteDao,
    private val userDetailDao: UserDetailDao
) : LocalDataSource {

    override suspend fun insertUsers(user: Users) =
        toResult { userDao.insert(user = user.users.map { it.toUserEntity() }.toTypedArray()) }

    override suspend fun insertUserDetail(user: UserDetail) =
        toResult { userDetailDao.insert(user.toUserDetailEntity()) }


    override fun getUserDetailById(username: String) =
        userDetailDao.getByUsername(username)
            .map(UserDetailEntity::toUserDetailEntity)
            .toResult()

    override fun getUsersBy(username: String, page: Int, limit: Int) =
        userDao.getUsers(username, page - 1, limit)
            .onEmpty { emit(emptyList()) }
            .map {
                val totalCount = userDao.totalCount(username, page - 1, limit) ?: 0
                val totalPage = totalCount / PER_PAGE
                val nextPage = if (totalPage != 0) page + 1 else null
                Users(
                    it.map(UsersEntity::toUser),
                    currentPage = page,
                    nextPage = nextPage
                )
            }
            .toResult()


    override suspend fun favoriteTransaction(user: User, isAdded: Boolean): Result<Unit> =
        toResult {
            if (isAdded) favorite.insert(user.toFavoriteEntity()) else favorite.delete(user.toFavoriteEntity())
        }

    override suspend fun favoriteTransaction(user: UserDetail, isAdded: Boolean): Result<Unit> =
        toResult {
            if (isAdded) favorite.insert(user.toFavoriteEntity()) else favorite.delete(user.toFavoriteEntity())
        }

    override fun getFavorites() =
        favorite.getAll()
            .onEmpty { emit(listOf()) }
            .map {
                Users(
                    it.map(FavoriteEntity::toUser),
                    1,
                    null
                )
            }
            .toResult()


    private suspend fun <T> toResult(block: suspend CoroutineScope.() -> T): Result<T> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val data = block()
                Result.success(data)
            } catch (e: Exception) {
                Log.i("MyLogger", "FromLocal ${e.message}")
                Result.failure(e)
            }
        }

    private fun <T> Flow<T>.toResult() =
        flowOn(ioDispatcher)
            .map { Result.success(it) }
            .catch {
                Log.i("MyLogger", "FromLocal ${it.message}")
                emit(Result.failure(it))
            }

}
