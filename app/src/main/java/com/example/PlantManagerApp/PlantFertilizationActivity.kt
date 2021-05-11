package com.example.PlantManagerApp

import CustomAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import app.com.q3.DataModel

class PlantFertilizationActivity : AppCompatActivity() {

    private var dataModel: ArrayList<DataModel>? = null
    private lateinit var listView: ListView
    private lateinit var adapter: CustomAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plant_fertilization)

        title = "Plant fertilization"
        listView = findViewById<View>(R.id.listView) as ListView
        dataModel = ArrayList<DataModel>()
        dataModel!!.add(DataModel("Азалия", true))
        dataModel!!.add(DataModel("Каланхоэ", true))
        dataModel!!.add(DataModel("Герань", false))
        dataModel!!.add(DataModel("Фиалка", true))

        adapter = CustomAdapter(dataModel!!, applicationContext)
        listView.adapter = adapter
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: DataModel = dataModel!![position] as DataModel
            dataModel.checked = !dataModel.checked
            adapter.notifyDataSetChanged()
        }

    }

    fun to_today(view: View) {
        val TodayIntent = Intent(this, TodayActivity::class.java)
        startActivity(TodayIntent)
    }
}