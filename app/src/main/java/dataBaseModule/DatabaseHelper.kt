package dataBaseModule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, version) {

    companion object {
        private val version = 1
        private const val dbName = "plants.db"
    }

    private lateinit var context: Context

//    fun DatabaseHelper(context: Context?) {
//        if (context == null) {
//            throw RuntimeException("context not found")
//        }
//            this.context = context
//    }

    fun exec(query: String, db: SQLiteDatabase) {
        try {
            db.execSQL(query)
        } catch (e: SQLiteException) {
            if (e.message != null && e.message!!.contains("not an error")) {
                return
            }
            throw e
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.w(null, "AAA")
        val queryToCreateDB = DBCreate().str
        val queryToFillDB = DBFill().str
        queryToCreateDB.split(";").stream().forEach { x -> run { exec(x, db) } }
        queryToFillDB.split(";").stream().forEach { x -> run {exec(x, db) } }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            onCreate(db)
        }
    }
}