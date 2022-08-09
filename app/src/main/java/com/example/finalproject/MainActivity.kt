package com.example.finalproject

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Api.SoccerData
import com.example.finalproject.adapters.SoccerAdapter
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val BASE_URL= "https://api.football-data.org/"
    private val plan= "TIER_ONE"
    //Activity for competions
    val league_list = ArrayList<fixtures>()

    val adapter = SoccerAdapter(league_list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startRegisterActivity()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.leaguesRecyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val football_api = retrofit.create(SoccerData::class.java)
        //Call to get a list of leagues from API
        football_api.getCompetitions(plan).enqueue(object :
            Callback<competitons> {

            override fun onResponse(
                call: Call<competitons>,
                response: Response<competitons>
            ) {
                Log.d(TAG, "onResponse: $response")

                val body = response.body()

                if (body == null) {
                    Log.w(TAG, "Valid response was not received")
                    return
                }

                league_list.addAll(body.competitions)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<competitons>, t: Throwable) {
                Log.d(TAG, "onFailure : $t")
            }



        })




    }



//Register/Login and menu
    private fun startRegisterActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
        return when (item.itemId) {
            R.id.logout_action -> {

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            startRegisterActivity()
                        }
                        else {
                            Log.e(TAG, "Task is not successful:${task.exception}")
                        }
                    }
                true
            }
            else -> {

                super.onOptionsItemSelected(item)
            }
        }
    }








}