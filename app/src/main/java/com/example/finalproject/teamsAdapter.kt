package com.example.finalproject

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore


//Adapter for teams
private lateinit var fireBaseDb: FirebaseFirestore
class teamsAdapter (private val teams : List<Team>) : RecyclerView.Adapter<teamsAdapter.MyViewHolder>()

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

        Glide.with(context)
            .load(currentItem.crest)
            .disallowHardwareConfig()
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.img)

        // Additional info to be used in info fragment
        val context2 = holder.itemView.context
        holder.img.setOnClickListener{
            val myIntent = Intent(context2, MatchesActivity::class.java)
            myIntent.putExtra("id",id)
            //myIntent.putExtra("club",club)



            context2.startActivity(myIntent)


        }
        // This is for add team
        button.setOnClickListener{
            fireBaseDb = FirebaseFirestore.getInstance()
            val fav_teams = fireBaseDb.collection("teams")
            val add = holder.itemView.context
            val teams = fav_team(
                club,
                logo.toString()
            )


            val id = fav_teams.document().id
            fav_teams.document(id).set(teams)
            Toast.makeText(context, "Saved!", Toast.LENGTH_SHORT).show()

        }








    }

    override fun getItemCount(): Int {
      return teams.size
    }

}