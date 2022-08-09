package com.example.finalproject.adapters

import android.app.Dialog
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject.FavoriteTeams
import com.example.finalproject.R
import com.example.finalproject.fav_team
import com.google.firebase.firestore.FirebaseFirestore
import org.w3c.dom.Text


//Adapter class used for favorite teams
private lateinit var fireBaseDb: FirebaseFirestore
class dataAdapter(private val teamData: List<fav_team>): RecyclerView.Adapter<dataAdapter.MyViewHolder>() {


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {



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
        val tla = currentItem.abb
        val year = currentItem.year

        val venue = currentItem.stadium
        val colors = currentItem.colors

        val team =currentItem.team
        val context = holder.itemView.context
        val web = currentItem.website
        val button = holder.delete_btn

        Glide.with(context)
            .load(currentItem.logo)
            .placeholder(R.drawable.ball_null) //Placeholder image for when image path doesnt exist
            .fitCenter()
            .into(holder.logo_hold)




        holder.personTeam.text = team
        val id = currentItem.id.toString()

        //Deleting a saved team from database
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
        //Show custom dialog for additional information on a saved team
        holder.itemView.setOnClickListener {
            Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show()
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.custom_dialog)
            val club = dialog.findViewById<TextView>(R.id.club)
            val tla1 = dialog.findViewById<TextView>(R.id.tla)
            val year1 = dialog.findViewById<TextView>(R.id.year)
            val venue1 = dialog.findViewById<TextView>(R.id.venue)
            val colors1 = dialog.findViewById<TextView>(R.id.colors)
            val btn = dialog.findViewById<Button>(R.id.button2)
            //Open website for team
            btn.setOnClickListener{
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(web)
                startActivity(context,intent,null)
            }
            club.text = team
            tla1.text = tla
            year1.text = year.toString()
            venue1.text = venue
            colors1.text = colors


            dialog.show()





        }




    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return teamData.size
    }

}
