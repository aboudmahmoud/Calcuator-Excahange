package com.example.calcuatorexcahange.data.repostory

import android.util.Log
import com.example.calcuatorexcahange.data.remote.ExchangeApi
import com.example.calcuatorexcahange.pojo.ExchangeModel
import com.example.calcuatorexcahange.utils.UiState
import javax.inject.Inject

class ExchacgeImplemtns @Inject constructor(
    private val exchangeApi: ExchangeApi
) {
    suspend  fun getexchangeData(): UiState<ExchangeModel> {
        val response = try {
            Log.d("Succes Aboud", "getForescateData:suc ")
            exchangeApi.getexchangeData()
        } catch (e: Exception) {
            Log.d("Abous", "getForescateData:error ${e.message}")
            return UiState.Failure("Error oucerd ${e.message}")
        }
        return UiState.Success(response)
    }
}