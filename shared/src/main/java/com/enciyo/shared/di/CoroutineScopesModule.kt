package com.enciyo.shared.di

import com.enciyo.shared.annotation.ApplicationScope
import com.enciyo.shared.annotation.Dispatcher
import com.enciyo.shared.GitDispatchers
import com.enciyo.shared.annotation.NetworkScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@Module
@InstallIn(SingletonComponent::class)
object CoroutineScopesModule {
    @Provides
    @ApplicationScope
    fun providesApplicationCoroutineScope(
        @Dispatcher(GitDispatchers.Default) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)

    @Provides
    @NetworkScope
    fun providesNetworkScope(
        @Dispatcher(GitDispatchers.IO) dispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}