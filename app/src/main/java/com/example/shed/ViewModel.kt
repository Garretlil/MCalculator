package com.example.shed

import android.app.Application
import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel



class CalculatorViewModel(application:Application): AndroidViewModel(application){

    var state by mutableStateOf(CalculatorState())
    var showtext:MutableState<String> = mutableStateOf("")
    var db:IRepository= DatabaseHelper(application)
    var ListCalcDB :ListCalcDB=ListCalcDB(showtext,db)

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.ActionSymbol  -> ListCalcDB.addtoList(action)
            is CalculatorAction.ActionOperation  -> ListCalcDB.addtoList(action)
            else -> {return}
        }
    }
    fun onAction2(action:EnumCalculation){
        when (action){
            is EnumCalculation.Calculate -> ListCalcDB.Calculate()
            is EnumCalculation.Delete    -> ListCalcDB.Delete()
            is EnumCalculation.Clear     -> ListCalcDB.Clear()
            is EnumCalculation.Decimal   -> ListCalcDB.Decimal()
        }
    }
}
