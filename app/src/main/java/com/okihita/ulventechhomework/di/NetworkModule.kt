package com.okihita.ulventechhomework.di

import com.okihita.ulventechhomework.model.remote.HolidayApi
import com.okihita.ulventechhomework.model.remote.MockInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("MockInterceptor")
    fun provideMockInterceptor() = MockInterceptor()

    @Provides
    @Singleton
    @Named("OkHttpClient")
    fun provideOkHttpClient(
        @Named("MockInterceptor") interceptor: Interceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://holidayapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideHolidayApiService(retrofit: Retrofit) =
        retrofit.create<HolidayApi>()
}