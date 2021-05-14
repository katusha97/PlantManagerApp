package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import dataBaseModule.DatabaseHandlerImpl
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val handler = DatabaseHandlerImpl(this)
        val calendar = findViewById<CalendarView>(R.id.calendarView)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR).toString()
        var month = c.get(Calendar.MONTH).toString()
        var day = c.get(Calendar.DAY_OF_MONTH).toString()
        if (month.toInt() < 10) {
            month = "0$month"
        }
        if (day.toInt() < 10) {
            day = "0$day"
        }
        val dataList = handler.getAllWorkForDay("$year-$month-$day")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = ""
        for (data in dataList) {
            val id = data.plant_id
            val name: String = handler.getPlantNameById(id)
            textView.append("Полить $name")
        }
    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}

