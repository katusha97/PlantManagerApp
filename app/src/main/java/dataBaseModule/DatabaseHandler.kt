package dataBaseModule

interface DatabaseHandler {
    fun addRoom (left_up: Int, right_down: Int)
    fun addPlant (plant_id: Int, name: String, room_id: Int, watering_day: String,
                  transplant_day: String, fertilization_day: String, period_id: Int,
                  coord_x: Double, coord_y: Double)

    fun removeRoom (room_id: Int)
    fun removePlant (name: String)
    fun removeWork (date: String, nameOfWork: String, plant_id: Int)

    fun setNotifTime (notif_time: String, every_X_minute: Int)
    fun setLanguage (language: Int)
    fun setNewUser (name: String, email: String, birthday: String)
    fun setPeriodOfLife (plant_id: Int)
    fun updatePlantCoord(name: String, coord_x: Double, coord_y: Double)
    fun updateDateOfWork(date: String, plant_id: Int, name: String, new_date: String)
    fun updateStatusOfWork(date: String, plant_id: Int, name: String, new_status: Int)

    fun getPlant(name: String): Plant
    fun getAllRooms (): List<Room>
    fun getAllPlantsOfUser (): List<Plant>
    fun getAllPlantByRoom (room_id: Int): List<Plant>
    fun getDescription (name: String): String
    fun getNotifTime (): String
    fun getLanguage (): String
    fun getAllWorkForDay (date: String): List <Work>
    fun getPeriodOfLife (name: String): String
    fun getSeason(): String
    fun getCharctOfPlant (name: String, period_of_life: Int, season: String): List<Characteristics>
}