package com.example.finalproject

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.Api.SoccerData
import com.example.finalproject.adapters.matchAdapter
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
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
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startRegisterActivity()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.matches_ad)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val football_api = retrofit.create(SoccerData::class.java)
        val data = intent.getIntExtra("id", 2021)
        //val passID = intent.getStringExtra("saved_id")
        //Log.d(ContentValues.TAG, "ID Number passed: $passID")
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
    fun go_back(view: View){
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)

        finish()
    }
    fun go_saved(view: View){
       // val passID = intent.getStringExtra("saved_id")
        val intent = Intent(this, FavoriteTeams::class.java)
       // intent.putExtra("passID",passID)
        startActivity(intent)

        finish()

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
                            Log.e(ContentValues.TAG, "Task is not successful:${task.exception}")
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