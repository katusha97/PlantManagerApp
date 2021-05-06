package dataBaseModule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.io.File
import java.lang.RuntimeException

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbName, null, version) {

    companion object {
        private var mDataBase: SQLiteDatabase? = null
        private val version = 1
        private val dbName = "plants.db"
    }

    private lateinit var context: Context

    fun DatabaseHelper(context: Context?) {
        if (context == null) {
            throw RuntimeException("context not found")
        }
            this.context = context
    }

    override fun onCreate(db: SQLiteDatabase) {
        val queryToCreateBD = DBCreate().str
        val queryToFillBD = DBFill().str
        db.execSQL(queryToCreateBD)
        db.execSQL(queryToFillBD)
        mDataBase = db
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if (newVersion > oldVersion) {
            onCreate(db)
        }
    }

    private fun readFile(fileName: String): String {
        return File(fileName).inputStream().bufferedReader().use { it.readLine() }
    }
}