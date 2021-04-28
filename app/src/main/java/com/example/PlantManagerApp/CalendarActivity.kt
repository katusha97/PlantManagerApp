package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}