package com.example.PlantManagerApp

import android.app.Activity
import android.os.Bundle
import com.example.PlantManagerApp.CustomView

class ShowYourMapActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(CustomView(this))
    }
}