package com.enciyo.data.di

import com.enciyo.data.FlipperProvider
import com.enciyo.data.FlipperProviderImp
import com.enciyo.data.GithubService
import com.squareup.moshi.Moshi
import dagger.Binds
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
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Singleton
    @Binds
    fun bindFlipperProvider(flipperProviderImp: FlipperProviderImp): FlipperProvider

    companion object{
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
        fun provideRetrofit(client: OkHttpClient, factory: Converter.Factory): Retrofit =
            Retrofit.Builder()
                .client(client)
                .baseUrl("https://api.github.com/") // May move to BuildConfig and reorganize by build type. But not need right now.
                .addConverterFactory(factory)
                .build()

        @Provides
        fun provideGithubService(retrofit: Retrofit): GithubService =
            retrofit.create()
    }
}