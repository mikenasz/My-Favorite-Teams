package com.example.finalproject

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_rate.*

// Main fragement that holds the buttons to back and to next activity
class mainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_match, container, false)
        val go_btn = view.findViewById<Button>(R.id.go_to)
        val back_btn = view.findViewById<Button>(R.id.go_Back)

        //Go to favorite teams
        go_btn.setOnClickListener {
            val intent = Intent(activity, FavoriteTeams::class.java)

            startActivity(intent)


        }
        //Go back to leagues
        back_btn.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            startActivity(intent)


        }







        return view
    }
}