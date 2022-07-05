package com.example.finalproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

const val API_KEY = "X-Auth-Token: 959d2937581149adbf2f530b4b0f6861"
//API call for league and team data
interface SoccerData {
    @Headers(API_KEY)
    @GET("/v2/competitions")
    fun getCompetitions(
        @Query("plan") plan: String
    ): Call<competitons>

    @Headers(API_KEY)
    @GET("/v2/competitions/{id}/teams")
    fun getLeagues(
        @Path("id") id: String,
        @Query("season") year: String
    ): Call<teams_list>


}