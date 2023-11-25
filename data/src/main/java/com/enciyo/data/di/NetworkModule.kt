package com.enciyo.data.di

import com.enciyo.data.GithubService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import xin.sparkle.moshi.NullSafeKotlinJsonAdapterFactory
import xin.sparkle.moshi.NullSafeStandardJsonAdapters


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideMoshi(): Moshi =
        Moshi.Builder()
            .add(NullSafeStandardJsonAdapters.FACTORY)
            .add(NullSafeKotlinJsonAdapterFactory())
            .build()

    @Provides
    fun provideConverterFactory(moshi: Moshi) =
        MoshiConverterFactory.create(moshi)

    @Provides
    fun provideHttpClient() =
        OkHttpClient.Builder()
            .build()

    @Provides
    fun provideRetrofit(client: OkHttpClient, factory: Converter.Factory) =
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.github.com/") // May move to BuildConfig and reorganize by build type. But not need right now.
            .addConverterFactory(factory)
            .build()


    @Provides
    fun provideGithubService(retrofit: Retrofit) =
        retrofit.create<GithubService>()
}