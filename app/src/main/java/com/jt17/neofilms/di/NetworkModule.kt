package com.jt17.neofilms.di

import com.jt17.neofilms.networking.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://imdb-api.com/API/"

    @[Provides Singleton]
    fun provideJsonGsonConvertor(): GsonConverterFactory = GsonConverterFactory.create()

    @[Provides Singleton]
    fun provideHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.run {
            readTimeout(30, TimeUnit.SECONDS)
            connectTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
        }
        return builder.build()
    }

    @[Provides Singleton]
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(provideJsonGsonConvertor())
        .client(okHttpClient)
        .build()

    @[Provides Singleton]
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}