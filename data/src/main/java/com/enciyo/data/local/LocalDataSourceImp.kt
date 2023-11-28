package com.enciyo.data.local

import com.enciyo.data.local.dao.FavoriteDao
import com.enciyo.data.local.dao.UserDao
import com.enciyo.data.local.entity.FavoriteEntity
import com.enciyo.data.local.entity.UserEntity
import com.enciyo.data.local.mapper.toFavoriteEntity
import com.enciyo.data.local.mapper.toUser
import com.enciyo.data.local.mapper.toUserEntity
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
    private val favorite: FavoriteDao
) : LocalDataSource {

    override suspend fun insertUsers(user: Users) =
        toResult {
            userDao.insert(user = user.users.map { it.toUserEntity() }.toTypedArray())
        }

    override suspend fun getUsers(): Result<Users> = toResult {
        Users(
            userDao.getUsers().map(UserEntity::toUser),
            1,
            null
        )
    }

    override suspend fun deleteAllUsers() = toResult {
        userDao.deleteAll()
    }

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
                Result.failure(e)
            }
        }

    private fun <T> Flow<T>.toResult() =
        flowOn(ioDispatcher)
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }

}
