package com.game.game.web

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class ApiFactory {
    //    private val baseUrl = "https://v3.football.api-sports.io/"
//    private val baseUrl = "https://api-football-v1.p.rapidapi.com/v3/"
//
//    private val retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//
//    val apiService = retrofit.create(ApiService::class.java)
//}
    private val baseUrl = "https://api-football-v1.p.rapidapi.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val newRequest = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", "c9bef3598cmshb7d5bc1cabcd6cbp1b2d06jsn01daa63962c6")
                .addHeader("X-RapidAPI-Host", "api-football-v1.p.rapidapi.com")
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
    @GET("v3/fixtures?league=39&season=2023&from=2024-01-01&to=2024-01-02")
    suspend fun get(): Response<ResultJson>

    @GET("v3/fixtures")
    suspend fun get(@Query("league") league: String, @Query("season") season: String, @Query("from") from: String, @Query("to") to: String): Response<ResultJson>
}


//    //
//    @Headers(
//        "x-apisports-key: 1d5db7dc9a809c73b9307fc6e63ff912",
//        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
//    )
//    @GET("fixtures?league=39&season=2023&from=2023-01-01&to=2024-01-30/")
//   suspend fun get(): ResultJson

//   @Headers(
//        "x-apisports-key: 1d5db7dc9a809c73b9307fc6e63ff912",
//        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
//    )
//    @GET("fixtures?league=39&season=2023&from=2023-01-01&to=2024-01-30/")
//   suspend fun get(): Response<ResultJson>
//
//    @Headers(
//        "x-apisports-key: 1d5db7dc9a809c73b9307fc6e63ff912",
//        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
//    )
//    @GET("status/")
//    suspend fun get(): ResultJson


//    @Headers(
//        "X-RapidAPI-Key: c9bef3598cmshb7d5bc1cabcd6cbp1b2d06jsn01daa63962c6",
//        "X-RapidAPI-Host: api-football-v1.p.rapidapi.com"
//    )