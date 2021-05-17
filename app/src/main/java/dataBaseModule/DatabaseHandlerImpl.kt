package dataBaseModule

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import java.util.*

class DatabaseHandlerImpl(private val context: Context) : DatabaseHandler {

    // ВСЕ ЗНАЧЕНИЯ КАКИХ-ЛИБО ДАТ ДОЛЖНЫ БЫТЬ ЗАПИСАНЫ ВВИДЕ СТРОКИ В ТАКОМ ФОРМАТЕ:
    // YYYY-MM-DD, А ВРЕМЯ В ТАКОМ ВИДЕ HH:MM:SS

    private val myDBHelper = DatabaseHelper(context)
    private val dbWrite: SQLiteDatabase = myDBHelper.writableDatabase
    private val dbRead: SQLiteDatabase = myDBHelper.readableDatabase

    override fun addRoom(left_up: Double, right_down: Double) {
        dbWrite.execSQL("INSERT INTO rooms(left_up, right_down) VALUES (${left_up}, ${right_down})")
    }

    override fun addPlant(
        plant_id: Int, name: String, room_id: Int, watering_day: String,
        transplant_day: String, fertilization_day: String, period_id: Int,
        coord_x: Double, coord_y: Double
    ) {
        dbWrite.execSQL(
            "INSERT INTO plants_of_user VALUES (${plant_id}, \"${name}\", ${room_id}, \"${watering_day}\", " +
                    "\"${transplant_day}\", \"${fertilization_day}\", ${period_id}, ${coord_x}, ${coord_y})"
        )
    }

    override fun addWork(date: String, plant_name: String, work_type: WorkType) {
        dbWrite.execSQL(
            "INSERT INTO calendar VALUES (\"${date}\", \"${plant_name}\", ${work_type.ordinal})")
    }

    override fun removeRoom(room_id: Int) {
        dbWrite.delete("rooms", "room_id = $room_id", null)
    }

    override fun removePlant(name: String) {
        dbWrite.delete("plants_of_user", "name = \"$name\"", null)
    }

    override fun removeWork(date: String, plant_name: String, work_type: WorkType) {
        dbWrite.delete(
            "calendar",
            "day = \"$date\" and work_id = ${work_type.ordinal} " +
                    "and plant_name = \"$plant_name\"",
            null
        )
    }

    override fun setNotifTime(notif_time: String, every_X_minute: Int) {
        val values = ContentValues().apply {
            put("notification_time", notif_time)
            put("every_X_minute", every_X_minute)
        }
        dbWrite.update("user_app", values, null, null)
    }

    override fun setLanguage(language_id: Int) {
        val values = ContentValues().apply {
            put("language", language_id)
        }
        dbWrite.update("user_app", values, null, null)
    }

    override fun setNewUser(name: String, email: String, birthday: String) {
        val values = ContentValues().apply {
            put("name", name)
            put("email", email)
            put("birthday", birthday)
        }
        dbWrite.insert("user_app", null, values)
    }

    override fun setPeriodOfLife(name: String, period_id: Int) {
        val values = ContentValues().apply {
            put("period_id", period_id)
        }
        dbWrite.update("plants_of_user", values, "name = \"$name\"", null)
    }

    override fun updatePlantCoord(name: String, coord_x: Double, coord_y: Double) {
        val values = ContentValues().apply {
            put("coord_x", coord_x)
            put("coord_y", coord_y)
        }
        dbWrite.update("plants_of_user", values, "name = \"$name\"", null)
    }

    override fun updateDateOfWork(date: String, plant_name: String, work_type: WorkType, new_date: String) {
        val values = ContentValues().apply {
            put("new_date", new_date)
        }
        dbWrite.update(
            "calendar", values, "day = \"$date\" and " +
                    "plant_id = $plant_name and work_id = ${work_type.ordinal}", null
        )
    }

    @SuppressLint("Recycle")
    override fun getPlant(name: String): Plant {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user WHERE name = \"$name\"", null)
        return Plant(
            cursor.getString(cursor.getColumnIndex("plant_id")),
            cursor.getString(cursor.getColumnIndex("name")),
            cursor.getInt(cursor.getColumnIndex("room_id")),
            cursor.getString(cursor.getColumnIndex("watering_day")),
            cursor.getString(cursor.getColumnIndex("transplant_day")),
            cursor.getString(cursor.getColumnIndex("fertilization_day")),
            cursor.getInt(cursor.getColumnIndex("period_id")),
            cursor.getString(cursor.getColumnIndex("coord_x")).toDouble(),
            cursor.getString(cursor.getColumnIndex("coord_y")).toDouble()
        )
    }

    @SuppressLint("Recycle")
    override fun getAllRooms(): List<Room> {
        val listOfRoom = ArrayList<Room>()
        val cursor = dbRead.rawQuery("SELECT * FROM rooms", null)
        while (cursor.moveToNext()) {
            listOfRoom.add(
                Room(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getDouble(cursor.getColumnIndex("left_up")),
                    cursor.getDouble(cursor.getColumnIndex("right_down"))
                )
            )
        }
        return listOfRoom
    }

    @SuppressLint("Recycle")
    override fun getAllPlantsOfUser(): List<Plant> {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user", null)
        val listOfPlants = ArrayList<Plant>()
        while (cursor.moveToNext()) {
            listOfPlants.add(
                Plant(
                    cursor.getString(cursor.getColumnIndex("plant_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("room_id")),
                    cursor.getString(cursor.getColumnIndex("watering_day")),
                    cursor.getString(cursor.getColumnIndex("transplant_day")),
                    cursor.getString(cursor.getColumnIndex("fertilization_day")),
                    cursor.getInt(cursor.getColumnIndex("period_id")),
                    cursor.getString(cursor.getColumnIndex("coord_x")).toDouble(),
                    cursor.getString(cursor.getColumnIndex("coord_y")).toDouble()
                )
            )
        }
        return listOfPlants
    }

    @SuppressLint("Recycle")
    override fun getAllNameOfPlant(): List<String> {
        val cursor = dbRead.rawQuery("SELECT name from plants", null)
        val listOfName = ArrayList<String>()
        while (cursor.moveToNext()) {
            listOfName.add(cursor.getString(cursor.getColumnIndex("name")))
        }
        return listOfName
    }

    @SuppressLint("Recycle")
    override fun getAllPlantByRoom(room_id: Int): List<Plant> {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user WHERE room_id = $room_id", null)
        val listOfPlants = ArrayList<Plant>()
        while (cursor.moveToNext()) {
            listOfPlants.add(
                Plant(
                    cursor.getString(cursor.getColumnIndex("plant_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("room_id")),
                    cursor.getString(cursor.getColumnIndex("watering_day")),
                    cursor.getString(cursor.getColumnIndex("transplant_day")),
                    cursor.getString(cursor.getColumnIndex("fertilization_day")),
                    cursor.getInt(cursor.getColumnIndex("period_id")),
                    cursor.getString(cursor.getColumnIndex("coord_x")).toDouble(),
                    cursor.getString(cursor.getColumnIndex("coord_y")).toDouble()
                )
            )
        }
        return listOfPlants
    }

    @SuppressLint("Recycle")
    override fun getDescription(name: String): String {
        val id = getPlantIdByClientName(name)
        val cursor = dbRead.rawQuery("SELECT description FROM plants WHERE id = $id", null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("description"))
    }

    @SuppressLint("Recycle")
    override fun getNotifTime(): String {
        val cursor = dbRead.rawQuery("SELECT notification_time FROM user_app", null)
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("notification_time"))
    }

    @SuppressLint("Recycle")
    override fun getLanguage(): String {
        val cursor1 = dbRead.rawQuery("SELECT language_id FROM user_app", null)
        val cursor = dbRead.rawQuery(
            "SELECT name FROM language WHERE id = " +
                    "${cursor1.getColumnIndex("language_id")}", null
        )
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("name"))
    }

    @SuppressLint("Recycle")
    override fun getAllWorkForDay(date: String): List<Work> {
        val cursor = dbRead.rawQuery("SELECT * FROM calendar WHERE day = \"$date\"", null)
        val listOfWork = ArrayList<Work>()
        while (cursor.moveToNext()) {
            listOfWork.add(
                Work(
                    cursor.getString(cursor.getColumnIndex("day")),
                    cursor.getString(cursor.getColumnIndex("plant_name")),
                    WorkType.values()[cursor.getInt(cursor.getColumnIndex("work_id"))]
                )
            )
        }
        return listOfWork
    }


    @SuppressLint("Recycle")
    override fun getPeriodOfLife(name: String): String {
        val cursor1 =
            dbRead.rawQuery("SELECT period_id FROM plants_of_user WHERE name = \"$name\"", null)
        val cursor = dbRead.rawQuery(
            "SELECT name from period_of_life WHERE id = " +
                    "${cursor1.getInt(cursor1.getColumnIndex("period_id"))}", null
        )
        cursor.moveToFirst()
        return cursor.getString(cursor.getColumnIndex("name"))
    }

    @SuppressLint("Recycle")
    override fun getAllPeriodOfLife(): List<String> {
        val cursor = dbRead.rawQuery("SELECT name from period_of_life", null)
        val listOfPeriod = ArrayList<String>()
        while (cursor.moveToNext()) {
            listOfPeriod.add(cursor.getString(cursor.getColumnIndex("name")))
        }
        return listOfPeriod
    }

    override fun getSeason(): String {
        val c = Calendar.getInstance()
        return when (c.get(Calendar.MONTH)) {
            1, 2, 3 -> "winter"
            4, 5, 6 -> "spring"
            7, 8, 9 -> "summer"
            10, 11, 12 -> "fall"
            else -> ""
        }
    }

    override fun getCharctOfPlant(
        name: String,
        period_of_life: Int,
        season: String
    ): List<Characteristics> {
        TODO("Not yet implemented")
    }

    @SuppressLint("Recycle")
    override fun getPeriodId(name: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from period_of_life WHERE name = \"$name\"", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getPlantId(name: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from plants WHERE name = \"$name\"", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getPlantIdByClientName(name: String): Int {
        val cursor = dbRead.rawQuery("SELECT plant_id from plants_of_user WHERE name = \"$name\"", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getPlantNameById(id: Int): String {
        val cursor = dbRead.rawQuery("SELECT id from plants_of_user WHERE id = id", null)
        cursor.moveToFirst()
        return cursor.getColumnName(cursor.getColumnIndex("name"))
    }

    @SuppressLint("Recycle")
    override fun getWatering(id: Int, season: Int): Int {
        val cursor: Cursor
        when(season) {
            1 -> cursor = dbRead.rawQuery("SELECT every_X_day from watering_winter WHERE plant_id = $id", null)
            2 -> cursor = dbRead.rawQuery("SELECT every_X_day from watering_spring WHERE plant_id = $id", null)
            3 -> cursor = dbRead.rawQuery("SELECT every_X_day from watering_summer WHERE plant_id = $id", null)
            else -> cursor = dbRead.rawQuery("SELECT every_X_day from watering_fall WHERE plant_id = $id", null)
        }
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getTransplant(id: Int): Int {
        val cursor = dbRead.rawQuery("SELECT every_X_month from plant_transplant WHERE plant_id = $id", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getFertilization(id: Int): Int {
        val cursor = dbRead.rawQuery("SELECT every_X_month from plant_fertilization WHERE plant_id = $id", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getLoosening(id: Int): Int {
        val cursor = dbRead.rawQuery("SELECT every_X_day from plant_loosening WHERE plant_id = $id", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getSpraying(id: Int): Int {
        val cursor = dbRead.rawQuery("SELECT every_X_day from plant_spraying WHERE plant_id = $id", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getWorkId(work: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from work_enum WHERE work = \"$work\"", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }

    @SuppressLint("Recycle")
    override fun getLanguageId(language_name: String): Int {
        val cursor =
            dbRead.rawQuery("SELECT id from language WHERE name = \"$language_name\"", null)
        cursor.moveToFirst()
        return cursor.getInt(0)
    }
}
