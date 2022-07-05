package com.example.finalproject

import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.android.synthetic.main.activity_favorite_teams.*
//Class uses firebase to show users favorite teams
class FavoriteTeams : AppCompatActivity() {
    private lateinit var fireBaseDb: FirebaseFirestore




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_teams)
        fireBaseDb = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null) {
            startRegisterActivity()
        }

        show()



    }

    //Show teams added to firebase in app
    fun show(){
        fireBaseDb.collection("teams")
            .orderBy("id")
            .addSnapshotListener{ snapshots, e ->
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e)
                    return@addSnapshotListener
                }

                if (snapshots != null) {

                    Log.d(TAG, "onEvent: -----------------------------")

                    //val stringBuilder = StringBuilder()

                    val teamss = snapshots.toObjects<fav_team>()


                    getData(teamss)

                } else {
                    Log.d(TAG, "Current data: null")
                }
            }

    }

    //Recycler view

    fun getData(show_team : List<fav_team>){

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = dataAdapter(show_team)
        recyclerView.layoutManager = LinearLayoutManager(this)

    }
    //go back to previous page
    fun go_back(view: View){
        val myIntent = Intent()
        setResult(Activity.RESULT_CANCELED,myIntent)
        finish()
    }




    //Delete a team from database
    fun delete(view: View){
        val id = delete_id.text.toString()
        if (id.isNotEmpty()) {


            fireBaseDb.collection("teams")
                .whereEqualTo("id", id.toInt())
                .get()
                .addOnSuccessListener {documents->

                    for (document in documents) {
                        if (document != null) {
                            Log.d(TAG, "${document.id} => ${document.data}")
                            // delete the document
                            document.reference.delete()

                            clearEditTexts()


                            break
                        } else{
                            Log.d(TAG, "No such document")
                        }
                    }
                }

        }



    }

    private fun clearEditTexts(){

        delete_id.text.clear()

    }
    private fun startRegisterActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout_action -> {

                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show()

                AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            startRegisterActivity()
                        } else {
                            Log.e(TAG, "Task is not successful:${task.exception}")
                        }
                    }
                true
            }
            else -> {

                super.onOptionsItemSelected(item)
            }
        }


    }
}







