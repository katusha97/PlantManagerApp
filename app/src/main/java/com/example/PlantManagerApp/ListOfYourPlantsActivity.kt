package com.example.PlantManagerApp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl
import java.util.*


class ListOfYourPlantsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_of_your_plants)

        val handler = DatabaseHandlerImpl(this)
        val users = handler.getAllPlantsOfUser()

        val arrayAdapter: ArrayAdapter<*>
//        val users = listOf(
//                "Азалия", "Каланхоэ", "Герань", "Фиалка"
//        )

        val names = arrayListOf<String>()

        for (item in users){
            names.add(item.name)
        }


        var mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, names)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position)
            val intent = Intent(this, DeletePlantActivity::class.java)
            intent.putExtra("KEY",element);
            startActivity(intent)
        }


    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}