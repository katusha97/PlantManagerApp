package dataBaseModule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.io.File
import java.lang.RuntimeException
import java.nio.file.Path
import java.nio.file.Paths

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


//    companion object {
//        private const val DB_NAME = "plants.db"
//        private var DB_PATH = ""
//        private const val DB_VERSION = 1
//    }
//
//    init {
//        DB_PATH = if (Build.VERSION.SDK_INT >= 17) context.applicationInfo.dataDir + "/databases/" else "" + context.packageName + "/databases/"
//        mContext = context
//        copyDataBase()
//        this.readableDatabase
//    }
//
//    @Throws(IOException::class)
//    fun updateDataBase() {
//        if (mNeedUpdate) {
//            val dbFile = File(DB_PATH + DB_NAME)
//            if (dbFile.exists()) dbFile.delete()
//            copyDataBase()
//            mNeedUpdate = false
//        }
//    }
//
//    private fun checkDataBase(): Boolean {
//        val dbFile = File(DB_PATH + DB_NAME)
//        return dbFile.exists()
//    }
//
//    private fun copyDataBase() {
//        if (!checkDataBase()) {
//            this.readableDatabase
//            close()
//            try {
//                copyDBFile()
//            } catch (mIOException: IOException) {
//                throw Error("ErrorCopyingDataBase")
//            }
//        }
//    }
//
//    @Throws(IOException::class)
//    private fun copyDBFile() {
//        val mInput = mContext.assets.open(DB_NAME)
//        val mOutput: OutputStream = FileOutputStream(DB_PATH + DB_NAME)
//        val mBuffer = ByteArray(1024)
//        var mLength: Int
//        while (mInput.read(mBuffer).also { mLength = it } > 0) mOutput.write(mBuffer, 0, mLength)
//        mOutput.flush()
//        mOutput.close()
//        mInput.close()
//    }
//
//    @Throws(SQLException::class)
//    fun openDataBase(): Boolean {
//        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY)
//        return mDataBase != null
//    }
//
//    @Synchronized
//    override fun close() {
//        if (mDataBase != null) mDataBase!!.close()
//        super.close()
//    }