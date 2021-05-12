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

    override fun addPlant(plant_id: Int, name: String, room_id: Int, watering_day: String,
                          transplant_day: String, fertilization_day: String, period_id: Int,
                          coord_x: Double, coord_y: Double) {
        dbWrite.execSQL("INSERT INTO plants_of_user VALUES (${plant_id}, \"${name}\", ${room_id}, \"${watering_day}\", " +
                "\"${transplant_day}\", \"${fertilization_day}\", ${period_id}, ${coord_x}, ${coord_y})")
    }

    override fun removeRoom(room_id: Int) {
        dbWrite.delete("rooms", "room_id = $room_id", null)
    }

    override fun removePlant(name: String) {
        dbWrite.delete("plants_of_user", "name = \"$name\"", null)
    }

    override fun removeWork(date: String, work_id: Int, plant_id: Int) {
        dbWrite.delete("calendar", "date = \"$date\" and work_id = $work_id and plant_id = $plant_id", null)
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

    override fun updateDateOfWork(date: String, plant_id: Int, work_id: Int, new_date: String) {
        val values = ContentValues().apply {
            put("new_date", new_date)
        }
        dbWrite.update("calendar", values, "day = \"$date\" and " +
                "plant_id = $plant_id and work_id = $work_id", null)
    }

    override fun updateStatusOfWork(date: String, plant_id: Int, work_id: Int, new_status: Int) {
        val values = ContentValues().apply {
            put("new_status", new_status)
        }
        dbWrite.update("calendar", values, "day = \"$date\" and " +
                "plant_id = $plant_id and work_id = $work_id", null)
    }

    @SuppressLint("Recycle")
    override fun getPlant(name: String): Plant {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user WHERE name = \"$name\"", null)
        return Plant(cursor.getString(cursor.getColumnIndex("plant_id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getColumnIndex("room_id"),
                cursor.getString(cursor.getColumnIndex("watering_day")),
                cursor.getString(cursor.getColumnIndex("transplant_day")),
                cursor.getString(cursor.getColumnIndex("fertilization_day")),
                cursor.getColumnIndex("period_id"),
                cursor.getDouble("coord_x"),
                cursor.getDouble("coord_y"))
    }

    @SuppressLint("Recycle")
    override fun getAllRooms(): List<Room> {
        val listOfRoom = ArrayList<Room>()
        val cursor = dbRead.rawQuery("SELECT * FROM rooms", null)
        cursor.moveToFirst()
        while (cursor.isAfterLast) {
            listOfRoom.add(Room(cursor.getColumnIndex("id"),
                    cursor.getDouble(cursor.getColumnIndex("left_up")),
                    cursor.getDouble(cursor.getColumnIndex("right_down"))))
            cursor.moveToNext()
        }
        return listOfRoom
    }

    @SuppressLint("Recycle")
    override fun getAllPlantsOfUser(): List<Plant> {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user", null)
        val listOfPlants = ArrayList<Plant>()
        cursor.moveToFirst()
        while (cursor.isAfterLast) {
            listOfPlants.add(Plant(cursor.getString(cursor.getColumnIndex("plant_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getColumnIndex("room_id"),
                    cursor.getString(cursor.getColumnIndex("watering_day")),
                    cursor.getString(cursor.getColumnIndex("transplant_day")),
                    cursor.getString(cursor.getColumnIndex("fertilization_day")),
                    cursor.getColumnIndex("period_id"),
                    cursor.getDouble("coord_x"),
                    cursor.getDouble("coord_y")))
            cursor.moveToNext()
        }
        return listOfPlants
    }

    @SuppressLint("Recycle")
    override fun getAllPlantByRoom(room_id: Int): List<Plant> {
        val cursor = dbRead.rawQuery("SELECT * FROM plants_of_user WHERE room_id = $room_id", null)
        val listOfPlants = ArrayList<Plant>()
        cursor.moveToFirst()
        while (cursor.isAfterLast) {
            listOfPlants.add(Plant(cursor.getString(cursor.getColumnIndex("plant_id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getColumnIndex("room_id"),
                    cursor.getString(cursor.getColumnIndex("watering_day")),
                    cursor.getString(cursor.getColumnIndex("transplant_day")),
                    cursor.getString(cursor.getColumnIndex("fertilization_day")),
                    cursor.getColumnIndex("period_id"),
                    cursor.getDouble("coord_x"),
                    cursor.getDouble("coord_y")))
            cursor.moveToNext()
        }
        return listOfPlants
    }

//    @SuppressLint("Recycle")
//    override fun getDescription(name: String): String {
//        val id = getPlantId(name)
//        val cursor = dbRead.rawQuery("SELECT description FROM plants WHERE id = $id", null)
//        return cursor.getString(cursor.getColumnIndex("description"))
//    }

    @SuppressLint("Recycle")
    override fun getDescription(name: String): String {
        return "This is really good!"
    }

    @SuppressLint("Recycle")
    override fun getNotifTime(): String {
        val cursor = dbRead.rawQuery("SELECT notification_time FROM user_app", null)
        return cursor.getString(cursor.getColumnIndex("notification_time"))
    }

    @SuppressLint("Recycle")
    override fun getLanguage(): String {
        val cursor1 = dbRead.rawQuery("SELECT language_id FROM user_app", null)
        val cursor = dbRead.rawQuery("SELECT name FROM language WHERE id = " +
                "${cursor1.getColumnIndex("language_id")}", null)
        return cursor.getString(cursor.getColumnIndex("name"))
    }

    @SuppressLint("Recycle")
    override fun getAllWorkForDay(date: String): List<Work> {
        val cursor = dbRead.rawQuery("SELECT * FROM calendar WHERE day = \"$date\"", null)
        val listOfWork = ArrayList<Work>()
        cursor.moveToFirst()
        while (cursor.isAfterLast) {
            listOfWork.add(Work(cursor.getString(cursor.getColumnIndex("day")),
                    cursor.getColumnIndex("plant_id"),
                    cursor.getColumnIndex("work_id"),
                    cursor.getColumnIndex("status")))
            cursor.moveToNext()
        }
        return listOfWork
    }



    @SuppressLint("Recycle")
    override fun getPeriodOfLife(name: String): String {
        val cursor1 = dbRead.rawQuery("SELECT period_id FROM plants_of_user WHERE name = \"$name\"", null)
        val cursor = dbRead.rawQuery("SELECT name from period_of_life WHERE id = " +
                "${cursor1.getColumnIndex("period_id")}", null)
        return cursor.getString(cursor.getColumnIndex("name"))
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

    override fun getCharctOfPlant(name: String, period_of_life: Int, season: String): List<Characteristics> {
        TODO("Not yet implemented")
    }

    @SuppressLint("Recycle")
    override fun getPeriodId(name: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from period_of_life WHERE name = \"$name\"", null)
        return cursor.getColumnIndex("id")
    }

    @SuppressLint("Recycle")
    override fun getPlantId(name: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from plants_of_user WHERE name = \"$name\"", null)
        return cursor.getColumnIndex("id")
    }

    @SuppressLint("Recycle")
    override fun getWorkId(work: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from work_enum WHERE work = \"$work\"", null)
        return cursor.getColumnIndex("id")
    }

    @SuppressLint("Recycle")
    override fun getLanguageId(language_name: String): Int {
        val cursor = dbRead.rawQuery("SELECT id from language WHERE name = \"$language_name\"", null)
        return cursor.getColumnIndex("id")
    }
}

private fun Cursor.getDouble(s: String): Double {
    return s.toDouble()
}
