package dataBaseModule

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.sql.SQLException


class DBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @Throws(IOException::class)
    fun createDataBase() {
        val dbExist = checkDataBase()
        if (dbExist) {
            //ничего не делаем – файл базы данных уже есть
        } else {
            this.readableDatabase
            try {
                copyDataBase()
            } catch (e: IOException) {
                throw Error("Error copying database")
            }
        }
    }

    private fun checkDataBase(): Boolean {
        var checkDB: SQLiteDatabase? = null
        try {
            val myPath = DB_PATH + DB_NAME
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY)
        } catch (e: SQLiteException) {
            //файл базы данных отсутствует
        }
        checkDB?.close()
        return checkDB != null
    }

    @Throws(IOException::class)
    private fun copyDataBase() {
        val input: InputStream = context?.getAssets()?.open(DB_NAME) ?: throw SQLException()
        val outFileName = DB_PATH + DB_NAME
        val output: OutputStream = FileOutputStream(outFileName)
        val buffer = ByteArray(1024)
        var length: Int
        while (input.read(buffer).also { length = it } > 0) {
            output.write(buffer, 0, length)
        }
        output.flush()
        output.close()
        input.close()
    }

    @Throws(SQLException::class)
    fun openDataBase() {
        val path = DB_PATH + DB_NAME
        dataBase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY)
    }

    @Synchronized
    override fun close() {
        dataBase?.close()
        super.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    companion object {
        private const val DB_PATH = "src/main/res/assets/"
        private const val DB_NAME = "plants.sql"
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "plantManager"
        private var dataBase: SQLiteDatabase? = null
        @SuppressLint("StaticFieldLeak")
        private val context: Context? = null
    }
}


//DbHelper helper = new DbHelper(this);
//try {
//    helper.createDataBase();
//} catch (IOException ioe) {
//    throw new Error("Не возможно инициализировать базу данных");
//}
//try {
//    helper.openDataBase();
//} catch (SQLException e) {
//    throw e;
//}