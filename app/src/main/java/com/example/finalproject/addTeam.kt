package com.example.finalproject

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_team.*
//Add a team and save it firebase
class addTeam : AppCompatActivity() {
    private lateinit var fireBaseDb: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_team)
        fireBaseDb = FirebaseFirestore.getInstance()

        //Get data passed from intent
        val data1 = intent.getStringExtra("club")
        val text = findViewById<TextView>(R.id.test)
        text.text =  "${data1.toString()}"

        val holder = findViewById<ImageView>(R.id.imgviewLogo)
        val img = intent.getStringExtra("logo")
            img.toString()
            // val passId = intent.getIntExtra()



        //Image
        Glide.with(this)
            .load(img)
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder)

    }

    //Save team data to firebase
    fun saveData(view: View){
        val fav_teams = fireBaseDb.collection("teams")
        //val myRatingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        val teams = fav_team(

         name_input.text.toString(),
           test.text.toString(),
            ratingBar.rating.toString()

        )


        val id = fav_teams.document().id
        fav_teams.document(id).set(teams)

        val myIntent = Intent()
        setResult(Activity.RESULT_CANCELED,myIntent)
        finish()
    }



}