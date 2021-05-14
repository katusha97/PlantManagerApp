package com.example.PlantManagerApp

import android.content.Intent
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.DatabaseHelper
import java.util.*


class ListOfYourPlantsActivity : AppCompatActivity() {

    private var mDBHelper: DatabaseHelper? = null
    private var mDb: SQLiteDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_list_of_your_plants)

        mDBHelper = DatabaseHelper(this)
        mDb = try {
            mDBHelper!!.writableDatabase
        } catch (mSQLException: SQLException) {
            throw mSQLException
        }

        val handler = DatabaseHandlerImpl(this)
        val users = handler.getAllPlantsOfUser()

        val arrayAdapter: ArrayAdapter<*>

        val names = arrayListOf<String>()

        for (item in users){
            names.add(item.name)
        }


        val mListView = findViewById<ListView>(R.id.userlist)
        arrayAdapter = ArrayAdapter(this,
                android.R.layout.simple_list_item_1, names)
        mListView.adapter = arrayAdapter

        mListView.setOnItemClickListener { parent, view, position, id ->
            val element = arrayAdapter.getItem(position)
            val intent = Intent(this, DeletePlantActivity::class.java)
            intent.putExtra("KEY", element);
            startActivity(intent)
        }


    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}