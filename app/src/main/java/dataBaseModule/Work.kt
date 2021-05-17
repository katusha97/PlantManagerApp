package dataBaseModule

enum class WorkType {
    WATERING,
    TRANSPLANT,
    FERTILIZATION,
    LOOSENING,
    SPRAYING
}

class Work (val day: String, val plant_name: String, val work_type: WorkType)
