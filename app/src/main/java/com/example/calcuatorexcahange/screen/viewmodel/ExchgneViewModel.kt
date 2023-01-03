package com.example.calcuatorexcahange.screen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcuatorexcahange.data.repostory.ExchacgeImplemtns
import com.example.calcuatorexcahange.pojo.ExchangeModel
import com.example.calcuatorexcahange.screen.Intent.ExchaneIntent
import com.example.calcuatorexcahange.utils.CoinesState
import com.example.calcuatorexcahange.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchgneViewModel @Inject constructor(
    repo: ExchacgeImplemtns
) : ViewModel() {
    //Our Intent Channel
    val IntentChanenl = Channel<ExchaneIntent> { Channel.UNLIMITED }
    //Here is our viewState Here We now if The data is loading or error or success
     var _ViewState = MutableStateFlow<UiState<ExchangeModel>>(UiState.Idel)
    val viewState: StateFlow<UiState<ExchangeModel>>  get() = _ViewState.asStateFlow()
    // is there Error we make true
    var ErrorStatus:Boolean by mutableStateOf(false)
    //Here We Stroe Our Error Message to display
    var ErrorMessage:String? by mutableStateOf(null)
    // if its loading
    var LoadingStatus:Boolean by mutableStateOf(false)
    //here if its Success we make Amount changed
    var AmoutCalacuted:Double? by mutableStateOf(null)
    //here for Selce in drop Box
    var selectedOptionText by mutableStateOf("AED")
    var selectedOptionText2 by mutableStateOf("USD")
    //Here is The Status of the Counes
    var coinState1: CoinesState by mutableStateOf(
        CoinesState.AED
    )
    var coinState2: CoinesState by mutableStateOf(
        CoinesState.USD
    )
    init {
        getTheRates(repo)
        processIntent()
    }

    private fun getTheRates(repo: ExchacgeImplemtns) {
        _ViewState.value = UiState.Loading
        viewModelScope.launch {
            _ViewState.value = try {
                val res = repo.getexchangeData()
                when (res) {
                    is UiState.Failure -> { UiState.Failure(res.error) }

                    is UiState.Success -> { UiState.Success(res.data) }
                    else -> { UiState.Loading }
                }

            } catch (e: Exception) {

                UiState.Failure("Error oucerd ${e.message}")

            }

        }
    }

    fun processIntent() {
        viewModelScope.launch {
            IntentChanenl.consumeAsFlow().collect {
                when (it) {
                    is ExchaneIntent.Calcuter -> {
                        setTheValueAfterExge(it.Amount)
                    }
                }

            }
        }
    }

    fun EvenEditInentent(Amoutnt:Double) {
        viewModelScope.launch {
            IntentChanenl.send(ExchaneIntent.Calcuter(Amoutnt))
        }
    }

    private fun setTheValueAfterExge(Amoutnt:Double){
       val res= viewState.value
      when(res){
          is UiState.Failure -> {
              ErrorMessage=res.error
              LoadingStatus=false
              ErrorStatus=true
          }
          is UiState.Success -> {
              LoadingStatus=false
              ErrorStatus=false
              currency_exchange_process(Amoutnt, res)
          }
         is UiState.Loading-> {
              UiState.Loading
              LoadingStatus=true
              ErrorStatus=false
         }
          UiState.Idel -> {
              LoadingStatus=false
              ErrorStatus=false
          }
      }

    }

    private fun currency_exchange_process(
        Amoutnt: Double,
        res: UiState.Success<ExchangeModel>
    ) {
        when (coinState1) {
            CoinesState.AED -> {
                ConvertAEDtoAllCouines(Amoutnt, res)
            }
            CoinesState.EGP -> {
                ConvertEGPtoAllCoines(Amoutnt, res)
            }
            CoinesState.USD -> {
                ConvertUSDToAllCoines(Amoutnt, res)
            }
        }
    }

    private fun ConvertUSDToAllCoines(
        Amoutnt: Double,
        res: UiState.Success<ExchangeModel>
    ) {
        when (coinState2) {
            CoinesState.AED -> {
                AmoutCalacuted = Amoutnt * res.data.rates!!.AED!!
            }
            CoinesState.EGP -> {
                AmoutCalacuted = (Amoutnt * res.data.rates!!.EGP!!)
            }
            CoinesState.USD -> {
                AmoutCalacuted = (Amoutnt * 1)

            }
        }
    }

    private fun ConvertEGPtoAllCoines(
        Amoutnt: Double,
        res: UiState.Success<ExchangeModel>
    ) {
        when (coinState2) {
            CoinesState.AED -> {
                AmoutCalacuted = (Amoutnt / res.data.rates!!.EGP!!) * res.data.rates!!.AED!!
            }
            CoinesState.EGP -> {
                AmoutCalacuted = (Amoutnt * 1)
            }
            CoinesState.USD -> {
                AmoutCalacuted = (Amoutnt / res.data.rates!!.EGP!!)
            }
        }
    }

    private fun ConvertAEDtoAllCouines(
        Amoutnt: Double,
        res: UiState.Success<ExchangeModel>
    ) {
        when (coinState2) {
            CoinesState.AED -> {
                AmoutCalacuted = Amoutnt * 1
            }
            CoinesState.EGP -> {
                AmoutCalacuted = (Amoutnt / res.data.rates!!.AED!!) * res.data.rates.EGP!!
            }
            CoinesState.USD -> {
                AmoutCalacuted = (Amoutnt / res.data.rates!!.AED!!)
            }
        }
    }

}