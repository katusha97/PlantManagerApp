package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import dataBaseModule.DatabaseHandlerImpl

class AboutThisAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_this_app)

        val textView = findViewById<TextView>(R.id.textViewAbout)
        textView.text = "Это приложение - учебный проект от Computer Science Center, магистерской программы JetBrains и ИТМО и Яндекс"
    }

    fun to_help(view: View) {
        val HelpIntent = Intent(this, HelpActivity::class.java)
        startActivity(HelpIntent)
    }
}
