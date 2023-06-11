package com.example.androidmusicplayer.db_controll

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.PlayList
import java.io.ByteArrayOutputStream


class ListDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "play_list_base.db"

        const val TABLE_NAME = "play_list_base"

        const val COLUMN1 = "id"
        const val COLUMN1_TYPE = "INTEGER PRIMARY KEY"

        const val COLUMN2 = "name"
        const val COLUMN2_TYPE = "TEXT UNIQUE"

        const val COLUMN3 = "comment"
        const val COLUMN3_TYPE = "TEXT"

        const val COLUMN4 = "bitmap"
        const val COLUMN4_TYPE = "BLOB"

        const val COLUMN5 = "user"
        const val COLUMN5_TYPE = "TEXT"
    }

    private fun insertData(values: ContentValues, database: String) {
        val db = writableDatabase
        val conflictAlgorithm = SQLiteDatabase.CONFLICT_REPLACE
        db.insertWithOnConflict(database, null, values, conflictAlgorithm)
        db.close()
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN1 $COLUMN1_TYPE, " +
                "$COLUMN2 $COLUMN2_TYPE, " +
                "$COLUMN3 $COLUMN3_TYPE, " +
                "$COLUMN4 $COLUMN4_TYPE, " +
                "$COLUMN5 $COLUMN5_TYPE)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insert(playList: PlayList){
        val value = ContentValues()
        value.put(COLUMN2, playList.name)
        value.put(COLUMN3, playList.comment)
        value.put(COLUMN4, playList.bitmap)
        value.put(COLUMN5, playList.user)
        insertData(value, TABLE_NAME)
    }

    fun remove(str: String){
        val db = readableDatabase
        val selection = "$COLUMN2 = ?"
        val selectionArgs = arrayOf(str)
        db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun query(str: String?): ArrayList<PlayList> {
        val list = ArrayList<PlayList>()
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5
        )
        var selectionArgs : Array<String>? = null
        val selection =
            if(str != null){
                selectionArgs = arrayOf("%$str%")
                "${LocalPlayDbHelper.COLUMN2} like ?"
            }
            else null
        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(COLUMN1))
                val name = it.getString(it.getColumnIndex(COLUMN2))
                val comment = it.getString(it.getColumnIndex(COLUMN3))
                val bitmap = it.getBlob(it.getColumnIndex(COLUMN4))
                val user = it.getString(it.getColumnIndex(COLUMN5))
                val helper = PlayDbHelper(context)
                val size = helper.queryByListId(id).size
                val playList = PlayList(name,user,comment, bitmap,size)
                list.add(playList)
            }
        }
        cursor?.close()
        db.close()
        return list
    }

    fun clear(){
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}