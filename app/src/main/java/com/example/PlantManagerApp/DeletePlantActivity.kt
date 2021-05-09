package com.example.PlantManagerApp
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class DeletePlantActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_plant)

        var button = findViewById(R.id.button_yes) as Button

        button.setOnClickListener {
//            var toast = Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG).show()
            val toast = Toast.makeText(this, "Deleted!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.TOP, 0, 1100)
            toast.show()
        }
        }

    fun to_list_of_your_plants(view: View) {
        val ListOfYourPlantsIntent = Intent(this, ListOfYourPlantsActivity::class.java)
        startActivity(ListOfYourPlantsIntent)
    }

}