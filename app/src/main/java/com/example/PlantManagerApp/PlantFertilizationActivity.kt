package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PlantFertilizationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_fertilization)
    }

    fun to_today(view: View) {
        val TodayIntent = Intent(this, TodayActivity::class.java)
        startActivity(TodayIntent)
    }
}