package com.example.finalproject

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.finalproject.Api.SoccerData
import com.example.finalproject.adapters.teamsAdapter
import com.firebase.ui.auth.AuthUI
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SecondActivity : AppCompatActivity() {
    private val BASE_URL = "https://api.football-data.org/"
    val teams_list = ArrayList<Team>()

    val adapter = teamsAdapter(teams_list)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startRegisterActivity()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.teams_list)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val football_api = retrofit.create(SoccerData::class.java)

        // Get the id passed from intent to display teams in leagues
        val data = intent.getIntExtra("id", 2021)
        val league = intent.getStringExtra("league_name")

        //val flag = intent.getStringExtra("flag")
        Log.d(TAG, "onResponse: $data")
        Log.d(TAG, "onResponse: $league")
        //Log.d(TAG, "onResponse: $flag")

        // Api call to get teams in that league
        football_api.getLeagues(data, "2021").enqueue(object :
            Callback<teams_list> {

            override fun onResponse(
                call: Call<teams_list>,
                response: Response<teams_list>
            ) {
                Log.d(ContentValues.TAG, "onResponse: $response")

                val body = response.body()

                if (body == null) {
                    Log.w(ContentValues.TAG, "Valid response was not received")
                    return
                }

                teams_list.addAll(body.teams)
                adapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<teams_list>, t: Throwable) {
                Log.d(ContentValues.TAG, "onFailure : $t")
            }


        })


    }
    // Logout abiltiy
    private fun startRegisterActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_action -> {

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            startRegisterActivity()
                        } else {
                            Log.e(TAG, "Task is not successful:${task.exception}")
                        }
                    }
                true
            }
            R.id.go_home -> {

                Toast.makeText(this, "Back Home", Toast.LENGTH_SHORT).show()
                goHome()
               true
            }
            R.id.savedteam_action -> {

                goHome()
                true
            }
            else -> {

                super.onOptionsItemSelected(item)
            }
        }


    }
}