package com.example.PlantManagerApp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import androidx.annotation.RequiresApi
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.WorkType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarActivity : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val handler = DatabaseHandlerImpl(this)
        val calendar = findViewById<CalendarView>(R.id.calendarView)
        val year = LocalDate.now().year.toString()
        var month = LocalDate.now().monthValue.toString()
        var day = LocalDate.now().dayOfMonth.toString()
        if (month.toInt() < 10) {
            month = "0$month"
        }
        if (day.toInt() < 10) {
            day = "0$day"
        }
        var dataList = handler.getAllWorkForDay("$year-$month-$day")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = ""
        if (dataList.isNotEmpty()) {
            for (data in dataList) {
                textView.append(getMessage(data.work_type, data.plant_name))
            }
        } else {
            textView.text = "На сегодня дел нет:)"
        }

        val calendarView = findViewById<CalendarView>(R.id.calendarView);
        calendarView.isClickable = true
        calendarView.setOnDateChangeListener { _, _, monthChoose, dayOfMonth ->
            val monthChooseRight = monthChoose + 1
            month = if (monthChooseRight < 10) {
                "0$monthChooseRight"
            } else  {
                "$monthChooseRight"
            }
            if (dayOfMonth < 10) {
                day = "0$dayOfMonth"
            } else {
                day = "$dayOfMonth"
            }
            dataList = handler.getAllWorkForDay("$year-$month-$day")
            textView.text = ""
            if (dataList.isNotEmpty()) {
                for (data in dataList) {
                    textView.append(getMessage(data.work_type, data.plant_name))
                }
            } else {
                textView.text = "На сегодня дел нет:)"
            }
        }
    }

    fun getMessage(workType: WorkType, name: String): String {
        when (workType) {
            WorkType.WATERING -> {
                return "Полить растение под названием $name\n"
            }
            WorkType.TRANSPLANT -> {
                return  "Пересадить растение под названием $name\n"
            }
            WorkType.FERTILIZATION -> {
                return "Удобрить растение под названием $name\n"
            }
            WorkType.LOOSENING -> {
                return "Прополоть почву у растения под названием $name\n"
            }
            WorkType.SPRAYING -> {
                return "Опрыскать растение под названием $name\n"
            }
        }
    }

    fun to_main(view: View) {
        val MainIntent = Intent(this, MainActivity::class.java)
        startActivity(MainIntent)
    }
}

