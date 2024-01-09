package com.game.game.web

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiFactory {

//    private val baseUrl = "https://api-football-v1.p.rapidapi.com/v3/"
    private val baseUrl = "https://v3.football.api-sports.io/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
//                .addHeader("X-RapidAPI-Key", "c9bef3598cmshb7d5bc1cabcd6cbp1b2d06jsn01daa63962c6")
//                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
                .addHeader("x-apisports-key", "1d5db7dc9a809c73b9307fc6e63ff912")
                .addHeader("x-rapidapi-host", "api-football-v1.p.rapidapi.com")
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