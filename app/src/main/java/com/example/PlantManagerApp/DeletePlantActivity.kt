package com.example.PlantManagerApp
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl


class DeletePlantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_plant)

        val handler = DatabaseHandlerImpl(this)
        val textView = findViewById<TextView>(R.id.textView_info)

        val extras = intent.extras
        var value = ""
        if (extras != null) {
            value = extras.getString("KEY").toString()
            textView.append(value)
            textView.append(": ")
            textView.append(handler.getDescription(value)) //тут фиксированный ответ
        }

        val button = findViewById(R.id.button_yes) as Button

        button.setOnClickListener {
//            handler.removePlant(value)
            val toast = Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 1150)
            toast.show()
        }
        }

    fun to_list_of_your_plants(view: View) {
        val ListOfYourPlantsIntent = Intent(this, ListOfYourPlantsActivity::class.java)
        startActivity(ListOfYourPlantsIntent)
    }

}