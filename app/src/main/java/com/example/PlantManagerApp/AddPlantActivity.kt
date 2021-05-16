package com.example.PlantManagerApp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl

class AddPlantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val handler = DatabaseHandlerImpl(this)

//        val nameOfPlant = handler.getAllNameOfPlant().toArray()
        val nameOfPlant = arrayOf("1", "2", "3")
        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameOfPlant)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }




    }

}