package com.enciyo.data.di

import com.enciyo.data.RepositoryImp
import com.enciyo.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bind(repositoryImp: RepositoryImp): Repository
}