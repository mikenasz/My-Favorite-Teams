package com.example.finalproject

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
//Adapter for teams
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
        val tla = currentItem.tla
        val founded = currentItem.founded
        val clubColors = currentItem.clubColors
        val button = holder.btn
        val venue= currentItem.venue
        val context  =  holder.itemView.context
        val logo = currentItem.crestUrl

        Glide.with(context)
            .load(currentItem.crestUrl)
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.img)

        // Additional info to be used in info fragment
        val context2 = holder.itemView.context
        holder.img.setOnClickListener{
            val myIntent = Intent(context2, RatingActivity::class.java)
            myIntent.putExtra("id",id)
            myIntent.putExtra("club",club)
            myIntent.putExtra("tla",tla)
            myIntent.putExtra("founded",founded)
            myIntent.putExtra("clubColors",clubColors)
            myIntent.putExtra("venue",venue)

            context2.startActivity(myIntent)


        }
        // This is for add team
        button.setOnClickListener{
            val add = holder.itemView.context
            val myIntent = Intent(add, addTeam::class.java )
            myIntent.putExtra("id",id)
            myIntent.putExtra("club",club)
            myIntent.putExtra("logo",logo)
            add.startActivity(myIntent)
        }








    }

    override fun getItemCount(): Int {
      return teams.size
    }

}