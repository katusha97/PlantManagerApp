package com.example.PlantManagerApp

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import dataBaseModule.DatabaseHandlerImpl
import dataBaseModule.WorkType
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class AddPlantActivity : AppCompatActivity() {

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_plant)

        val handler = DatabaseHandlerImpl(this)

        val nameOfPlant = handler.getAllNameOfPlant()


        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, nameOfPlant)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

        val nameOfPeriod = handler.getAllPeriodOfLife()
        val adapter1 =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, nameOfPeriod)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner1 = findViewById<Spinner>(R.id.spinner1)
        spinner1.adapter = adapter1


        var plant_id: Int? = null
        var name = ""
        var watering_day = ""
        var period_id: Int? = null

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                plant_id = handler.getPlantId(spinner.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                period_id = handler.getPeriodId(spinner1.selectedItem.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val pickerDate = findViewById<TextView>(R.id.date_picker_actions)
        pickerDate.setOnClickListener {
            val mcurrentDate = Calendar.getInstance()
            var mYear = mcurrentDate[Calendar.YEAR]
            var mMonth = mcurrentDate[Calendar.MONTH]
            var mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
            val mDatePicker = DatePickerDialog(
                this,
                { datepicker, selectedyear, selectedmonth, selectedday ->
                    val myCalendar: Calendar = Calendar.getInstance()
                    myCalendar.set(Calendar.YEAR, selectedyear)
                    myCalendar.set(Calendar.MONTH, selectedmonth)
                    myCalendar.set(Calendar.DAY_OF_MONTH, selectedday)
                    val myFormat = "yyyy-MM-dd"
                    val sdf = SimpleDateFormat(myFormat, Locale.ENGLISH)
                    pickerDate.setText(sdf.format(myCalendar.time))
                    mDay = selectedday
                    mMonth = selectedmonth
                    mYear = selectedyear
                }, mYear, mMonth, mDay
            )
            mDatePicker.show()
        }


        val button = findViewById<Button>(R.id.button_add)
        button.setOnClickListener {
            name = findViewById<EditText>(R.id.text_name).text.toString()
            watering_day = pickerDate.text.toString()
            if (name == "" || watering_day == "" || plant_id == null || period_id == null) {
                val textWrong = findViewById<TextView>(R.id.wrong)
                textWrong.setTextColor(Color.RED)
                textWrong.text = "Пожалуйста, заполните все поля!"
            } else {
                handler.addPlant(
                    plant_id!!.toInt(), name, 1, watering_day, watering_day, watering_day,
                    period_id!!.toInt(), 0.9, 0.9
                )

                val ListOfYourPlantsIntent = Intent(this, ListOfYourPlantsActivity::class.java)
                startActivity(ListOfYourPlantsIntent)

                //1 - winter, 2 - spring, 3 - summer, 4 - fall
                var currSeason = -1
                currSeason = if (LocalDate.now().monthValue == 1 || LocalDate.now().monthValue == 2
                    || LocalDate.now().monthValue == 3
                ) {
                    1
                } else if (LocalDate.now().monthValue == 4 || LocalDate.now().monthValue == 5
                    || LocalDate.now().monthValue == 6
                ) {
                    2
                } else if (LocalDate.now().monthValue == 7 || LocalDate.now().monthValue == 8
                    || LocalDate.now().monthValue == 9
                ) {
                    3
                } else {
                    4
                }
                var everyXDayWatering = -1
                var everyXMonthTransplant = -1
                var everyXMonthFertilization = -1
                var everyXDayLoosening = -1
                var everyXDaySpraying = -1
                if (plant_id != null && currSeason != -1) {
                    everyXDayWatering = handler.getWatering(plant_id!!, currSeason)
                    everyXMonthTransplant = handler.getTransplant(plant_id!!)
                    everyXMonthFertilization = handler.getFertilization(plant_id!!)
                    everyXDayLoosening = handler.getLoosening(plant_id!!)
                    everyXDaySpraying = handler.getSpraying(plant_id!!)
                }
                if (name != "" && watering_day != "") {
                    var currDateWatering = watering_day
                    var currDateTransplant = watering_day
                    var currDateFertilization = watering_day
                    var currDateLoosening = watering_day
                    var currDateSpraying = watering_day
                    for (i in 0 until 10) {
                        currDateWatering = getNextDate(currDateWatering, everyXDayWatering)
                        currDateTransplant = getNextDate(
                            currDateTransplant,
                            everyXMonthTransplant * 30
                        )
                        currDateFertilization = getNextDate(
                            currDateFertilization,
                            everyXMonthFertilization * 30
                        )
                        handler.addWork(currDateWatering, name, WorkType.WATERING)
                        handler.addWork(currDateTransplant, name, WorkType.TRANSPLANT)
                        handler.addWork(currDateFertilization, name, WorkType.FERTILIZATION)
                        if (everyXDayLoosening != 0) {
                            currDateLoosening = getNextDate(currDateLoosening, everyXDayLoosening)
                            handler.addWork(currDateLoosening, name, WorkType.LOOSENING)
                        }
                        if (everyXDaySpraying != 0) {
                            currDateSpraying = getNextDate(currDateSpraying, everyXDaySpraying)
                            handler.addWork(currDateSpraying, name, WorkType.SPRAYING)
                        }
                    }
                }
            }
        }
    }

    private fun getNextDate(currDate: String, everyXDay: Int): String {
        val splited = currDate.split("-")
        val year = splited[0].toInt()
        val month = if (splited[1].startsWith("0")) splited[1].substringAfterLast("0")
            .toInt() else (splited[1].toInt())
        val day = if (splited[2].startsWith("0")) splited[2].substringAfterLast("0")
            .toInt() else (splited[2].toInt())
        return LocalDate.of(year, month, day).plusDays(everyXDay.toLong()).format(
            DateTimeFormatter.ISO_LOCAL_DATE
        )
    }
}