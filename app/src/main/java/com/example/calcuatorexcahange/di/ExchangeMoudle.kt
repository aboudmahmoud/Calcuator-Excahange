package com.example.calcuatorexcahange.di

import com.example.calcuatorexcahange.data.remote.ExchangeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ExchangeMoudle {
    @Singleton
    @Provides
    fun provideExchangeAPI(retrofit: Retrofit): ExchangeApi {
        return retrofit.create(ExchangeApi::class.java)
    }

}