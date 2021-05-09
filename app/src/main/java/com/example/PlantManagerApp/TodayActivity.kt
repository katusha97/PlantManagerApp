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

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }

    fun to_watering(view: View) {
        val WateringIntent = Intent(this, WateringActivity::class.java)
        startActivity(WateringIntent)
    }
    fun to_plant_transplant(view: View) {
        val PlantTransplantIntent = Intent(this, PlantTransplantActivity::class.java)
        startActivity(PlantTransplantIntent)
    }
    fun to_plant_fertilization(view: View) {
        val PlantFertilizationIntent = Intent(this, PlantFertilizationActivity::class.java)
        startActivity(PlantFertilizationIntent)
    }
    fun to_loosening(view: View) {
        val LooseningIntent = Intent(this, LooseningActivity::class.java)
        startActivity(LooseningIntent)
    }
    fun to_spraying(view: View) {
        val SprayingIntent = Intent(this, SprayingActivity::class.java)
        startActivity(SprayingIntent)
    }

}