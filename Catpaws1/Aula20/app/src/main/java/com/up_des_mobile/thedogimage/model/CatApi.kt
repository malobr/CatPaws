package com.up_des_mobile.thedogimage.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface CatApi {
    @GET("v1/images/search")
    fun getCat(@Header("x-api-key") apiKey: String): Call<List<Cat>>
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.thecatapi.com/"

    val api: CatApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatApi::class.java)
    }
}