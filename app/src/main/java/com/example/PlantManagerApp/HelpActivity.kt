package com.example.PlantManagerApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HelpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }

    fun to_report_a_problem (view: View) {
        val ReportAProblemIntent = Intent(this, ReportAProblemActivity::class.java)
        startActivity(ReportAProblemIntent)
    }

    fun to_suggestion (view: View) {
        val SuggestionIntent = Intent(this, SuggestionActivity::class.java)
        startActivity(SuggestionIntent)
    }

    fun to_about_this_app (view: View) {
        val AboutThisAppIntent = Intent(this, AboutThisAppActivity::class.java)
        startActivity(AboutThisAppIntent)
    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}