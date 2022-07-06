package com.example.finalproject

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MatchesActivity : AppCompatActivity() {
    private val BASE_URL= "https://api.football-data.org/"
    val match_list = ArrayList<Match>()
    val adapter = matchAdapter(match_list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches)

        val recyclerView = findViewById<RecyclerView>(R.id.matches_ad)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val football_api = retrofit.create(SoccerData::class.java)
        val data = intent.getIntExtra("id", 2021)

        football_api.getMatches(data.toString(), "TIER_ONE","2021").enqueue(object :
            Callback<match_list> {

            override fun onResponse(
                call: Call<match_list>,
                response: Response<match_list>
            ) {
                Log.d(ContentValues.TAG, "onResponse: $response")

                val body = response.body()

                if (body == null) {
                    Log.w(ContentValues.TAG, "Valid response was not received")
                    return
                }

                match_list.addAll(body.matches)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<match_list>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure : $t")
            }


        })

    }
}