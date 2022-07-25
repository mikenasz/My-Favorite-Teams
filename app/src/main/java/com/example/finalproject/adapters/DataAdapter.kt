package com.example.finalproject.adapters

import android.content.ContentValues
import android.graphics.drawable.PictureDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.caverock.androidsvg.SVG
import com.example.finalproject.R
import com.example.finalproject.fav_team
import com.google.firebase.firestore.FirebaseFirestore


//Adapter class used for favorite teams
private lateinit var fireBaseDb: FirebaseFirestore
class dataAdapter(private val teamData: List<fav_team>): RecyclerView.Adapter<dataAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        //val personId = itemView.findViewById<TextView>(R.id.id_data)

        val personTeam = itemView.findViewById<TextView>(R.id.data_team)
        val logo_hold = itemView.findViewById<ImageView>(R.id.logo_saved)
        val delete_btn = itemView.findViewById<ImageButton>(R.id.deletebtn)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {


        // create a new view
        val view = LayoutInflater.from(parent.context).inflate(R.layout.data_item, parent, false)
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = teamData[position]

        val context = holder.itemView.context

        val button = holder.delete_btn

        Glide.with(context)
            .load(currentItem.logo)
            .placeholder(R.drawable.nullimg) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.logo_hold)




        holder.personTeam.text = currentItem.team
        val id = currentItem.id.toString()
       // holder.teamRating.text = "${currentItem.rating}/5 Stars"

        button.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete")
            builder.setMessage("Are you sure that you want to delete this team?")
            builder.setPositiveButton("Yes") { dialog, which ->


            fireBaseDb = FirebaseFirestore.getInstance()
            fireBaseDb.collection("teams")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener { documents ->

                    for (document in documents) {
                        if (document != null) {
                            //Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                            // delete the document
                            document.reference.delete()
                            break
                        } else {
                            Log.d(ContentValues.TAG, "No such document")

                        }
                    }

                }
                dialog.cancel()
                Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("No"){dialog, which ->
            dialog.cancel()
        }

            val dialog = builder.create()
            dialog.show()

        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return teamData.size
    }

}
