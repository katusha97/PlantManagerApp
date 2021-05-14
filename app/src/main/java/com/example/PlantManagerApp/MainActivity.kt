package com.example.PlantManagerApp

import android.content.Intent
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.DatabaseHelper


class MainActivity : AppCompatActivity() {

    private var mDBHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDBHelper = DatabaseHelper(this)
        mDb = try {
            mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }
    }

    fun to_today (view: View) {
        val TodayIntent = Intent(this, TodayActivity::class.java)
        startActivity(TodayIntent)
    }

    fun to_calendar (view: View) {
        val CalendarIntent = Intent(this, CalendarActivity::class.java)
        startActivity(CalendarIntent)
    }

    fun to_list_of_your_plants(view: View) {
        val ListOfYourPlantsIntent = Intent(this, ListOfYourPlantsActivity::class.java)
        startActivity(ListOfYourPlantsIntent)
    }

    fun to_show_your_map(view: View) {
        val ShowYourMapIntent = Intent(this, ShowYourMapActivity::class.java)
        startActivity(ShowYourMapIntent)
    }

    fun to_settings (view: View) {
        val SettingsIntent = Intent(this, SettingsActivity::class.java)
        startActivity(SettingsIntent)
    }

    fun to_help(view: View) {
        val HelpIntent = Intent(this, HelpActivity::class.java)
        startActivity(HelpIntent)
    }
}