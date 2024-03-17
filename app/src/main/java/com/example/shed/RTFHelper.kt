package com.example.shed

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

data class QuoteList(
    val id: String? = null,
    val name: String? = null
)

interface QuotesApi {
    @GET("/objects/ff8081818e21ce2d018e2bfbe59c02ae")
    suspend fun getQuotes() : Response<QuoteList>

    @POST("/objects")
    suspend fun postQuotes(@Body requestBody: QuoteList) : Response<QuoteList>

    @PUT("/objects/ff8081818e21ce2d018e2bfbe59c02ae")
    suspend fun putQuotes(@Body requestBody: QuoteList) : Response<QuoteList>
}

class RetrofitHelper:IRepository {
    val separateStr='|'
    private val baseUrl = "https://api.restful-api.dev"
    val quotesApi: QuotesApi = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(QuotesApi::class.java)

    suspend fun funA(): String {
        //do long work
        val result = quotesApi.getQuotes()
        val str = result.body()?.name.toString()
        return  str
    }
    fun saveDataStr(str:String){
        GlobalScope.launch {
            var ff = QuoteList("F11", str)
            quotesApi.putQuotes(ff)
        }
    }
    fun dataToStr(lst:ListCalc):String{
        var str:String=""
        for (i in 0..<lst.count()){
            if (lst[i] is ItemOper){//оператор
                str+=(lst[i] as ItemOper).oper.symbol+separateStr
            }
            else{
                str+=(lst[i] as ItemNumber).num+separateStr
            }
        }
        str.dropLast(1)
        return str
    }

//    override fun getData(lst: ListCalcDB) {
//        val t=ItemNumber()
//        t.num="5"
//        lst.add(t)
//    }
    override fun getData(lst: ListCalc) {
        var flag:Boolean=true
        var str: String = ""
            runBlocking {
                val jobA = async { funA() }
                runBlocking{
                    str = jobA.await()
                }
            }
        for (i in str.indices){
            if (str[i]!=separateStr) {
                if (flag) {
                    if (lst.isEmpty()) {
                        lst.add(ItemNumber())
                    }
                    if (lst[lst.count() - 1] is ItemNumber) {
                        lst[lst.count() - 1].Add(str[i].toString())
                    } else {
                        lst.add(ItemNumber())
                        lst[lst.count() - 1].Add(str[i].toString())
                    }
                }
                else{
                    val t=ItemOper()
                    t.oper= EnumOperation(str[i].toString()).getOper()
                    lst.add(t)
                }
            }
            else{flag=!flag}
        }
    }

    override fun saveData(lst: ListCalc) {
        val str=dataToStr(lst)
        GlobalScope.launch {
            var ff = QuoteList("F11", str)
            quotesApi.putQuotes(ff)
        }
    }

}