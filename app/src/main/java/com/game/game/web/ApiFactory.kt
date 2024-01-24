package com.game.game.web

import android.content.Context
import com.game.game.R
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiFactory(private val context: Context) {

    private val baseUrl = "https://v3.football.api-sports.io/"
//    private val baseUrl = "https://api-football-v1.p.rapidapi.com/v3/"


    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("x-apisports-key", context.getString(R.string.x_apisports_key))
                .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
//                .addHeader("X-RapidAPI-Key", "4b626e98f8mshb63b3e4b5b0cf3dp1ae234jsn37379ae25eed")
//                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .build()
            chain.proceed(newRequest)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService = retrofit.create(ApiService::class.java)
}

interface ApiService {
    @GET("fixtures?league=39&season=2023&from=2024-01-01&to=2024-01-02")
    suspend fun get(): Response<ResultJson>

    @GET("fixtures")
    suspend fun get(@Query("date") league: String): Response<ResultJson>

    @GET("fixtures")
    suspend fun get(
        @Query("league") league: Int,
        @Query("season") season: Int,
        @Query("from") from: String,
        @Query("to") to: String
    ): Response<ResultJson>
}