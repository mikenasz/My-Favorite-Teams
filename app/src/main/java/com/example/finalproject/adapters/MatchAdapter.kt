package com.example.finalproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.Match
import com.example.finalproject.R

class matchAdapter(private val matches: List<Match>): RecyclerView.Adapter<matchAdapter.MyViewHolder>() {

    inner class MyViewHolder (itemView: View): RecyclerView.ViewHolder (itemView){
        val date = itemView.findViewById<TextView>(R.id.match_date)
        val homeTeam = itemView.findViewById<TextView>(R.id.home_team)
        val awayTeam = itemView.findViewById<TextView>(R.id.away_team)
        val homeScore = itemView.findViewById<TextView>(R.id.home_score)
        val awayScore = itemView.findViewById<TextView>(R.id.away_score)
        val homeLogo = itemView.findViewById<ImageView>(R.id.home_img)
        val awayLogo = itemView.findViewById<ImageView>(R.id.away_img)



    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.match_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = matches[position]
        holder.date.text = currentItem.utcDate
        holder.homeTeam.text = currentItem.homeTeam.name
        holder.awayTeam.text = currentItem.awayTeam.name
        holder.homeScore.text = currentItem.score.fullTime.home.toString()
        holder.awayScore.text = currentItem.score.fullTime.away.toString()


        val context = holder.itemView.context

        Glide.with(context)
            .load(currentItem.homeTeam.crest)
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.homeLogo)
        Glide.with(context)
            .load(currentItem.awayTeam.crest)
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.awayLogo)




    }

    override fun getItemCount(): Int {
     return matches.size
    }

}