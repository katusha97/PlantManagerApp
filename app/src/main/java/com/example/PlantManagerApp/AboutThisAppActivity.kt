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

        val handler = DatabaseHandlerImpl(this)
        val textView = findViewById<TextView>(R.id.textViewAbout)
        handler.getDescription("about").also { textView.text = it } //потому что нет функции, где будет инфа о приложении
        //поэтому воспользовалась своей, которая возвращает фиксированное значение для растения
    }

    fun to_help(view: View) {
        val HelpIntent = Intent(this, HelpActivity::class.java)
        startActivity(HelpIntent)
    }
}
