package com.example.finalproject.adapters

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.MatchesActivity
import com.example.finalproject.R
import com.example.finalproject.Team
import com.example.finalproject.fav_team
import com.google.firebase.firestore.FirebaseFirestore


//Adapter for teams
private lateinit var fireBaseDb: FirebaseFirestore
class teamsAdapter (private val teams : List<Team>, private val season: String, private val league: Int) : RecyclerView.Adapter<teamsAdapter.MyViewHolder>()

{
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        val teamName = itemView.findViewById<TextView>(R.id.team_name)
        val img = itemView.findViewById<ImageView>(R.id.logoImage)
        val btn = itemView.findViewById<Button>(R.id.add_to)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_item, parent, false)
        return MyViewHolder(view)
    }
    override fun onBindViewHolder(holder: teamsAdapter.MyViewHolder, position: Int) {
        val currentItem = teams[position]
        holder.teamName.text = currentItem.name
        val id = currentItem.id
        val club = currentItem.name
        val button = holder.btn
        val context  =  holder.itemView.context
        val logo = currentItem.crest
        val year = currentItem.founded
        val abb = currentItem.tla
        val colors = currentItem.clubColors
        val stadium = currentItem.venue
        val website = currentItem.website

        Glide.with(context)
            .load(currentItem.crest)
            .disallowHardwareConfig()
            .placeholder(R.drawable.ball_null) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.img)
        fireBaseDb = FirebaseFirestore.getInstance()
        val fav_teams = fireBaseDb.collection("teams")
        val saved_id = fav_teams.document().id

        // On click listener for matches
        val context2 = holder.itemView.context
        holder.img.setOnClickListener{
            val myIntent = Intent(context2, MatchesActivity::class.java)
            myIntent.putExtra("id",id)
            myIntent.putExtra("club",club)
            //myIntent.putExtra("saved_id",saved_id)
            myIntent.putExtra("season", season)
            myIntent.putExtra("league", league)
            context2.startActivity(myIntent)


        }
        // Save a team to database
        button.setOnClickListener{


            val teams = fav_team(
                saved_id,
                club,
                logo,
                abb,
                stadium,
                year,
                colors,
                website
            )


           // val saved_id = fav_teams.document().id
            fav_teams.document(saved_id).set(teams)
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

        }








    }

    override fun getItemCount(): Int {
      return teams.size
    }

}