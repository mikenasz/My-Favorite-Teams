package com.example.finalproject

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
//Adapter for competitions
class SoccerAdapter(private val leagues : List<fixtures>) : RecyclerView.Adapter<SoccerAdapter.MyViewHolder>()
{
    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
            val title = itemView.findViewById<TextView>(R.id.league_name)
            val location = itemView.findViewById<TextView>(R.id.location_name)



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.league_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = leagues[position]
        holder.title.text = currentItem.name
        holder.location.text = currentItem.area.name
        val id = currentItem.passId

        val context = holder.itemView.context
        //Pass id to next activity with intent
        holder.itemView.setOnClickListener{

           val myIntent = Intent(context, SecondActivity::class.java)
                 myIntent.putExtra("id",id)

            context.startActivity(myIntent)

        }
    }



    override fun getItemCount(): Int {
        return leagues.size
    }

}

