package com.example.finalproject

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.fragment_rate.*

class RatingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rating)

        // Get data from previous activity to put in fragment
        val club_name = intent.getStringExtra("club")
        val founded = intent.getIntExtra("founded",0 )
        val tla = intent.getStringExtra("tla")
        val colors = intent.getStringExtra("clubColors")
        val venue = intent.getStringExtra("venue")




        val fragment1 = infoFragment()
        val fragment2 = mainFragment()

        val bundle1 = Bundle()
        // But data in bundle
        bundle1.putString("club",club_name)
        bundle1.putInt("founded",founded)
        bundle1.putString("tla",tla)
        bundle1.putString("clubColors",colors)
        bundle1.putString("venue",venue)

        // Put arguments in bundle for fragment 1 to use
        fragment1.arguments = bundle1

        //Start main fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, fragment2)
            .addToBackStack(null)
            .commit()
        //Landscape mode- fill with info fragment
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.Rating_container, fragment1)
                .addToBackStack(null)
                .commit()
        }




    }






}