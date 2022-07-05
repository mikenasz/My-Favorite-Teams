package com.example.finalproject

import com.google.gson.annotations.SerializedName
//Get leagues and competitons
data class fixtures(
    @SerializedName("id") val passId: Int,
    //val id : Int,
    val name : String,
    val area : Area,
)
//Get country
data class Area(
    val id : Int,
    val name : String,
)
//Lists of leagues and competitons
data class competitons (
    val count : Int,
    val competitions : List<fixtures>
    )

//list of teams
data class teams_list(
    val teams :List<Team>
)


//Team variables
data class Team(
    val name: String,
    val crestUrl: String?,
    val id : Int,
    val founded : Int,
    val clubColors : String,
    val tla : String,
    val venue : String



    )
//favorite team used to store into firebase
data class fav_team(
    var name : String? = null,
    var team : String? = null,
    var rating : String? = null,

)

