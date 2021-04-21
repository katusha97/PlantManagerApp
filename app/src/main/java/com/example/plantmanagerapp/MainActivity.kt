package com.example.plantmanagerapp

import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.PlantManagerapp.databinding.ActivityMainBinding
import dataBaseModule.DatabaseHelper
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private var mDBHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        mDBHelper = DatabaseHelper(this)
        try {
            mDBHelper!!.updateDataBase()
        } catch (mIOException: IOException) {
            throw Error("UnableToUpdateDatabase")
        }

        mDb = try {
            mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }

        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.button.setOnClickListener {
            var language = ""
            val cursor: Cursor = mDb!!.rawQuery("SELECT * FROM language", null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                language += cursor.getString(1).toString() + " | "
                cursor.moveToNext()
            }
            cursor.close()
            binding.textView.text = language
        }
    }
}