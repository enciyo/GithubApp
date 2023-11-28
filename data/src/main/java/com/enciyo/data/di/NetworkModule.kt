package com.enciyo.data.di

import com.enciyo.data.FlipperProviderImp
import com.enciyo.data.remote.FlipperProvider
import com.enciyo.data.remote.GithubService
import com.enciyo.data.remote.RemoteDataSource
import com.enciyo.data.remote.RemoteDataSourceImp
import com.enciyo.data.remote.adapter.ResultCallAdapterFactory
import com.enciyo.shared.annotation.NetworkScope
import com.squareup.moshi.Moshi
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindFlipperProvider(flipperProviderImp: FlipperProviderImp): FlipperProvider

    @Binds
    @Singleton
    fun bindRemoteDataSource(remoteDataSourceImp: RemoteDataSourceImp) :RemoteDataSource

    companion object {

        @Provides
        fun provideResultCallAdapterFactory(@NetworkScope scope: CoroutineScope): CallAdapter.Factory =
            ResultCallAdapterFactory(scope)

        @Provides
        fun provideMoshi(): Moshi =
            Moshi.Builder()
                .add(NullSafeStandardJsonAdapters.FACTORY)
                .add(NullSafeKotlinJsonAdapterFactory())
                .build()

        @Provides
        fun provideConverterFactory(moshi: Moshi): Converter.Factory =
            MoshiConverterFactory.create(moshi)

        @Provides
        fun provideHttpClient(flipperProvider: FlipperProvider): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(flipperProvider.get())
                .build()

        @Provides
        @Singleton
        fun provideRetrofit(
            client: OkHttpClient,
            converter: Converter.Factory,
            callAdapter: CallAdapter.Factory
        ): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com/") // May move to BuildConfig and reorganize by build type. But not need right now.
                .addConverterFactory(converter)
                .addCallAdapterFactory(callAdapter)
                .build()

        @Provides
        @Singleton
        fun provideGithubService(retrofit: Retrofit): GithubService =
            retrofit.create()
    }
}