package com.example.PlantManagerApp

import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHelper


class MainActivity : AppCompatActivity() {

    private var mDBHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mDBHelper = DatabaseHelper(this)
        val view = setContentView(R.layout.activity_main)

        mDb = try {
            mDBHelper!!.readableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        button.setOnClickListener {
            var language = ""
            val cursor: Cursor = mDb!!.rawQuery("SELECT * FROM language", null)
            while (cursor.moveToNext()) {
                val curr: String = cursor.getString(cursor.getColumnIndex("name"))
                language += "$curr | "
            }
            cursor.close()
            textView.text = language
        }
    }
}