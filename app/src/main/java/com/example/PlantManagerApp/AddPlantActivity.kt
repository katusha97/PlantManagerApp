package com.example.PlantManagerApp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl

class AddPlantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val handler = DatabaseHandlerImpl(this)

        val nameOfPlant = handler.getAllNameOfPlant()


        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameOfPlant)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        val nameOfPeriod = handler.getAllPeriodOfLife()
        val adapter1 = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, nameOfPeriod)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        spinner1.adapter = adapter1


        var plant_id : Int? = null
        var name : String = ""
        var watering_day : String = ""
        var period_id : Int? = null

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                plant_id = handler.getPlantId(spinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                period_id = handler.getPeriodId(spinner1.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



        val button = findViewById<Button>(R.id.button_add)
        button.setOnClickListener {
            name = findViewById<EditText>(R.id.text_name).text.toString()
            watering_day = findViewById<EditText>(R.id.text_date).text.toString()
            if (name == "" || watering_day == "" || plant_id == null || period_id == null) {

            } else {
                handler.addPlant(plant_id!!.toInt(), name, 1, watering_day, watering_day, watering_day,
                    period_id!!.toInt(), 0.9, 0.9)

                val ListOfYourPlantsIntent = Intent(this, ListOfYourPlantsActivity::class.java)
                startActivity(ListOfYourPlantsIntent)
            }
        }



    }

}