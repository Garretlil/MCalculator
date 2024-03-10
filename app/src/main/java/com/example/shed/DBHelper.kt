package com.example.shed

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.shed.CalculatorAction
import com.example.shed.EnumOperation
import com.example.shed.ItemNumber
import com.example.shed.ItemOper
import com.example.shed.ListCalc
import com.example.shed.ListCalcDB


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {

    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onCreate(db)
    }
    fun getData(lst: ListCalc)  {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM " + "DataCalc", null)
        val valueColumnIndex=cursor.getColumnIndex("Value")
        val typeColumnIndex=cursor.getColumnIndex("IdType")
        while (cursor.moveToNext()) {
            val v = cursor.getString(valueColumnIndex)
            val t =cursor.getString(typeColumnIndex)
            if (t.toInt()==1){
                var n=ItemNumber();
                n.num=v;
                lst.add(n)
            }
            else{
                var item_: ItemOper = ItemOper()
                item_.oper= EnumOperation(v).getOper()
                lst.add(item_)
            }
        }
    }
    fun saveData( lst:ListCalcDB) {
        val db = this.writableDatabase
        val sqlCREATEDataCalc= """
            CREATE TABLE IF NOT EXISTS  DataCalc (
                id INTEGER   PRIMARY KEY AUTOINCREMENT,
                IdType   INTEGER   ,
                Value  TEXT (10) 
            );          
          """
        
        db.execSQL(  sqlCREATEDataCalc )
        db.execSQL("DELETE FROM DataCalc")
        for (i in 0..<lst.count()){
            if (lst[i] is ItemNumber){
                val elem=(lst[i] as ItemNumber).num
                db.execSQL("insert into DataCalc(IdType,Value) values(1,$elem)")
            }
            else{
                val elem=(lst[i] as ItemOper).oper.symbol
                db.execSQL("insert into DataCalc(IdType,Value) values(2,'$elem')")
            }
        }
    }
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "CalcDB"
        private const val DataCalc = "DataCalc"

        //---------------
        private const val KEY_ID = "id"
        private const val KEY_EXPRESSION = "expression"
    }
}

