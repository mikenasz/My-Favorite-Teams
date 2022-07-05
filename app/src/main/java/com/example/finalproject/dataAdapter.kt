package com.example.finalproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//Adapter class used for favorite teams
class dataAdapter(private val teamData: List<fav_team>): RecyclerView.Adapter<dataAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //val personId = itemView.findViewById<TextView>(R.id.id_data)
        val personName = itemView.findViewById<TextView>(R.id.name_data)
        val personTeam = itemView.findViewById<TextView>(R.id.data_team)
        val teamRating = itemView.findViewById<TextView>(R.id.data_rating)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = teamData[position]

        holder.personName.text = currentItem.name
        holder.personTeam.text = currentItem.team
        holder.teamRating.text = "${currentItem.rating}/5 Stars"



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return teamData.size
    }

}
