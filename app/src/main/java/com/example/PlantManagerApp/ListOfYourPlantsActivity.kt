package com.example.PlantManagerApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import dataBaseModule.DatabaseHandlerImpl

class ListOfYourPlantsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_of_your_plants)

//        val handler = DatabaseHandlerImpl(this)
//        val users = handler.getAllPlantsOfUser()

        val arrayAdapter: ArrayAdapter<*>
        val users = listOf(
            "violet", "cactus", "aloe"
        )



        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, users)
        mListView.adapter = arrayAdapter
    }

}