package com.example.jsondeserializationprototype

import com.example.jsondeserializationprototype.gsonmodels.GsonSportsDataApiResponse
import com.example.jsondeserializationprototype.kotlinserializationmodels.KotlinSerializationSportsDataApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface WebService {

    @GET("StatsApiResponse.json")
    fun getScoreboardForKotlin(): Call<KotlinSerializationSportsDataApiResponse>

    @GET("StatsApiResponse.json")
    fun getScoreboardForGson(): Call<GsonSportsDataApiResponse>

    @GET("person.json")
    fun getPerson(): Call<MainActivity.Person>
}
