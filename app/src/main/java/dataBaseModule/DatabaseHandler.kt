package dataBaseModule

interface DatabaseHandler {
    fun addRoom (left_up: Double, right_down: Double)
    fun addPlant (plant_id: Int, name: String, room_id: Int, watering_day: String,
                  transplant_day: String, fertilization_day: String, period_id: Int,
                  coord_x: Double, coord_y: Double)

    fun removeRoom (room_id: Int)
    fun removePlant (name: String)
    fun removeWork (date: String,  work_id: Int, plant_id: Int)

    fun setNotifTime (notif_time: String, every_X_minute: Int)
    fun setLanguage (language_id: Int)
    fun setNewUser (name: String, email: String, birthday: String)
    fun setPeriodOfLife (name: String, period_id: Int)
    fun updatePlantCoord(name: String, coord_x: Double, coord_y: Double)
    fun updateDateOfWork(date: String, plant_id: Int, work_id: Int, new_date: String)
    fun updateStatusOfWork(date: String, plant_id: Int, work_id: Int, new_status: Int)

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
    fun getPeriodId(name: String): Int
    fun getWorkId(work: String): Int
    fun getLanguageId(language_name: String): Int
    fun getPlantId(name: String): Int
}