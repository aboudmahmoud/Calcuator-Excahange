package com.example.calcuatorexcahange.screen.Intent

sealed class ExchaneIntent{
    class Calcuter(val Amount:Double):ExchaneIntent()
}
