package com.enciyo.data.di

import android.content.Context
import androidx.room.Room
import com.enciyo.data.local.GithubDatabase
import com.enciyo.data.local.LocalDataSource
import com.enciyo.data.local.LocalDataSourceImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    @Binds
    @Singleton
    fun bind(localDataSourceImp: LocalDataSourceImp): LocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideRoomDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                context = context,
                klass = GithubDatabase::class.java,
                name = "GithubApp-Database" //Not safe
            )
                .fallbackToDestructiveMigration() //Coa
                .build()


        @Provides
        fun provideUserDao(githubDatabase: GithubDatabase) = githubDatabase.userDao

        @Provides
        fun provideFavoriteDao(githubDatabase: GithubDatabase) = githubDatabase.favoriteDao

        @Provides
        fun provideUserDetailDao(githubDatabase: GithubDatabase) = githubDatabase.userDetailDao

    }

}


