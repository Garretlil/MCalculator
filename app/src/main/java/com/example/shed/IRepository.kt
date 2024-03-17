package com.example.shed

interface IRepository {
    fun getData(lst: ListCalc)
    fun saveData(lst: ListCalc)
}