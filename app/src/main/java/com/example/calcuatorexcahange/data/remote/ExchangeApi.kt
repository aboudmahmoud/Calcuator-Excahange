package com.example.calcuatorexcahange.data.remote

import com.example.calcuatorexcahange.pojo.ExchangeModel
import retrofit2.http.GET

interface ExchangeApi {
    @GET("/v6/latest/USD")
    suspend fun getexchangeData(

    ): ExchangeModel
}