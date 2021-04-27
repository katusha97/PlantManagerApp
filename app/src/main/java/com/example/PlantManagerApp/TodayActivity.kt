package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TodayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today)
    }

    fun to_calendar_from_today(view: View) {
        val CalendarIntent = Intent(this, CalendarActivity::class.java)
        startActivity(CalendarIntent)
    }

}