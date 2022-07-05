package com.example.finalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_rate.*
import kotlinx.android.synthetic.main.fragment_rate.view.*


class infoFragment : Fragment() {



    //Display extra information about team when on landscape orientation
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_rate, container, false)

         val club_text = view.findViewById<TextView>(R.id.club_name)
        val tla_text = view.findViewById<TextView>(R.id.tla)
        val founded_text = view.findViewById<TextView>(R.id.founded)
        val color_text = view.findViewById<TextView>(R.id.colors)
        val venue_text = view.findViewById<TextView>(R.id.Stadium)



        //Retreive arguements passed from activity
        club_text.text = requireArguments().get("club").toString()
        tla_text.text = requireArguments().get("tla").toString()
        founded_text.text = requireArguments().getInt("founded").toString()
        color_text.text = requireArguments().get("clubColors").toString()
        venue_text.text = requireArguments().get("venue").toString()







        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}