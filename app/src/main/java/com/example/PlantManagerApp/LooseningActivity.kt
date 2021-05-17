package com.example.PlantManagerApp

import CustomAdapter
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.annotation.RequiresApi
import app.com.q3.DataModel
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.WorkType
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LooseningActivity : AppCompatActivity() {

    private var dataModel: ArrayList<DataModel> = mutableListOf<DataModel>() as ArrayList<DataModel>
    private lateinit var listView: ListView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loosening)
        val handler = DatabaseHandlerImpl(this)


        title = "Loosening"
        listView = findViewById<View>(R.id.listView) as ListView
        val dtime = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
        val listOfWork =
            handler.getAllWorkForDay(dtime)

        for (work in listOfWork) {
            if (work.work_type == WorkType.LOOSENING) {
                dataModel.add(DataModel(work.plant_name, false))
            }
        }

        adapter = CustomAdapter(dataModel, WorkType.LOOSENING, applicationContext)
        listView.adapter = adapter

    }

    fun to_today(view: View) {
        val TodayIntent = Intent(this, TodayActivity::class.java)
        startActivity(TodayIntent)
    }
}