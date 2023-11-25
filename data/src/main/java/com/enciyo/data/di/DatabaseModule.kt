package com.enciyo.data.di

import android.content.Context
import androidx.room.Room
import com.enciyo.data.local.GithubDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DatabaseModule {

    companion object {
        @Provides
        fun provideRoomDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                context = context,
                klass = GithubDatabase::class.java,
                name = "GithubApp-Database" //Not safe
            )
                .build()
    }

}


