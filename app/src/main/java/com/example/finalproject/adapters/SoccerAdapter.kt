package com.example.finalproject.adapters

import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.finalproject.SecondActivity
import com.example.finalproject.fixtures

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
        val passCountry = currentItem.name

        val context = holder.itemView.context
       //Dialog prompt to select a season
        holder.itemView.setOnClickListener{
            val list = arrayOf("2022", "2021", "2020")
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Select a Season")
            builder.setSingleChoiceItems(list, -1,
                DialogInterface.OnClickListener{dialog, i ->

                    val season = list[i]
                    val myIntent = Intent(context, SecondActivity::class.java)
                    myIntent.putExtra("id",id)
                    myIntent.putExtra("league_name",passCountry)

                    myIntent.putExtra("season", season)

                    context.startActivity(myIntent)
                    dialog.dismiss()
                }

                )
            builder.setNeutralButton("Cancel"){dialog, which ->
                dialog.cancel()
            }

            val dialog = builder.create()
            dialog.show()

        }
    }



    override fun getItemCount(): Int {
        return leagues.size
    }

}

