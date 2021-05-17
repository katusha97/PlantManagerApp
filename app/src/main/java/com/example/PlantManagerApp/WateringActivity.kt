package com.example.PlantManagerApp

import CustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import app.com.q3.DataModel
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.WorkType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.collections.ArrayList

class WateringActivity : AppCompatActivity() {

    private var dataModel: ArrayList<DataModel> = mutableListOf<DataModel>() as ArrayList<DataModel>
    private lateinit var listView: ListView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watering)
        val handler = DatabaseHandlerImpl(this)

        title = "Watering"
        listView = findViewById(R.id.listView)
        val dtime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val listOfWork = handler.getAllWorkForDay(dtime)

        for (work in listOfWork) {
            if (work.work_type == WorkType.WATERING) {
                dataModel.add(DataModel(work.plant_name, false))
            }
        }

        if (dataModel.isEmpty()) {
            return
        }

        adapter = CustomAdapter(dataModel, WorkType.WATERING, applicationContext)
        listView.adapter = adapter

    }

    fun to_today(view: View) {
        val TodayIntent = Intent(this, TodayActivity::class.java)
        startActivity(TodayIntent)
    }
}
