package dataBaseModule

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class DatabaseHandlerImpl(private val context: Context) : DatabaseHandler {

    private val myDBHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    fun openBD() {
        db = myDBHelper.writableDatabase
    }

    override fun addRoom(left_up: Int, right_down: Int) {
        TODO("Not yet implemented")
    }

    override fun addPlant(plant_id: Int, name: String, room_id: Int, watering_day: String, transplant_day: String, fertilization_day: String, period_id: Int, coord_x: Double, coord_y: Double) {
        TODO("Not yet implemented")
    }

    override fun removeRoom(room_id: Int) {
        TODO("Not yet implemented")
    }

    override fun removePlant(name: String) {
        TODO("Not yet implemented")
    }

    override fun removeWork(date: String, nameOfWork: String, plant_id: Int) {
        TODO("Not yet implemented")
    }

    override fun setNotifTime(notif_time: String, every_X_minute: Int) {
        TODO("Not yet implemented")
    }

    override fun setLanguage(language: Int) {
        TODO("Not yet implemented")
    }

    override fun setNewUser(name: String, email: String, birthday: String) {
        TODO("Not yet implemented")
    }

    override fun setPeriodOfLife(plant_id: Int) {
        TODO("Not yet implemented")
    }

    override fun updatePlantCoord(name: String, coord_x: Double, coord_y: Double) {
        TODO("Not yet implemented")
    }

    override fun updateDateOfWork(date: String, plant_id: Int, name: String, new_date: String) {
        TODO("Not yet implemented")
    }

    override fun updateStatusOfWork(date: String, plant_id: Int, name: String, new_status: Int) {
        TODO("Not yet implemented")
    }

    override fun getPlant(name: String): Plant {
        TODO("Not yet implemented")
    }

    override fun getAllRooms(): List<Room> {
        TODO("Not yet implemented")
    }

    override fun getAllPlantsOfUser(): List<Plant> {
        TODO("Not yet implemented")
    }

    override fun getAllPlantByRoom(room_id: Int): List<Plant> {
        TODO("Not yet implemented")
    }

    override fun getDescription(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getNotifTime(): String {
        TODO("Not yet implemented")
    }

    override fun getLanguage(): String {
        TODO("Not yet implemented")
    }

    override fun getAllWorkForDay(date: String): List<Work> {
        TODO("Not yet implemented")
    }

    override fun getPeriodOfLife(name: String): String {
        TODO("Not yet implemented")
    }

    override fun getSeason(): String {
        TODO("Not yet implemented")
    }

    override fun getCharctOfPlant(name: String, period_of_life: Int, season: String): List<Characteristics> {
        TODO("Not yet implemented")
    }

}