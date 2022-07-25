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
    val flag : String,
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
    val crest: String,
    val id : Int,
    val founded : Int,
    val clubColors : String,
    val tla : String,
    val venue : String



    )

data class match_list(
    val matches: List<Match>
)
data class Match(
    val utcDate : String ,
    val homeTeam: Home,
    val awayTeam : Away,
    val score : Score,
)
data class Home(
    val id: Int,
    val name :String,
    val crest :String
)
data class Away(
    val id: Int,
    val name:String,
    val crest:String
)
data class Score(
    val fullTime : full_Time,
)
data class full_Time(
    val home: Int,
    val away: Int,
)
//favorite team used to store into firebase
data class fav_team(
    var id : String? = null,
    var team : String? = null,
    var logo : String? = null,

)

