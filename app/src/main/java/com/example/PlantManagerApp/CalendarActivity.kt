package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import dataBaseModule.DatabaseHandlerImpl

class CalendarActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val handler = DatabaseHandlerImpl(this)
        val calendar = findViewById<CalendarView>(R.id.calendarView)
        val dataList = handler.getAllWorkForDay(calendar.date.toString())
        var textView = findViewById<TextView>(R.id.textView)
        textView.text = ""
//        for (data in dataList) {
//            val id = data.plant_id
//            val name: String = handler.getPlantName(id)
//            textView.append("Полить $name")
//        }


//        val dataList = arrayOf("a", "b", "c", "d", "e")
//        for (item in dataList){
//            textView.append(item)
//            textView.append("\n")
//        }
    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}

