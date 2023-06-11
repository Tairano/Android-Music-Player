package com.example.androidmusicplayer.db_controll

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.androidmusicplayer.media.TAG
import com.example.androidmusicplayer.struct.Play

class RecentDbHelper (context: Context) : DBHelper(context) {
    companion object {
        const val TABLE_NAME = "recent_base"

        const val COLUMN1 = "id"
        const val COLUMN1_TYPE = "INTEGER PRIMARY KEY"

        const val COLUMN2 = "title"
        const val COLUMN2_TYPE = "TEXT UNIQUE"

        const val COLUMN3 = "artist"
        const val COLUMN3_TYPE = "TEXT"

        const val COLUMN4 = "album"
        const val COLUMN4_TYPE = "TEXT"

        const val COLUMN5 = "path"
        const val COLUMN5_TYPE = "TEXT"

        const val COLUMN6 = "bitmap"
        const val COLUMN6_TYPE = "BLOB"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN1 $COLUMN1_TYPE, " +
                "$COLUMN2 $COLUMN2_TYPE, " +
                "$COLUMN3 $COLUMN3_TYPE, " +
                "$COLUMN4 $COLUMN4_TYPE, " +
                "$COLUMN5 $COLUMN5_TYPE, " +
                "$COLUMN6 $COLUMN6_TYPE)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insert(play: Play){
        val value = ContentValues()
        value.put(COLUMN2, play.title)
        value.put(COLUMN3, play.artist)
        value.put(COLUMN4, play.album)
        value.put(COLUMN5, play.path)
        value.put(COLUMN6, play.bitmap)
        insertData(value, TABLE_NAME)
    }

    fun remove(str: String){
        val db = readableDatabase
        val selection = "$COLUMN2 = ?"
        val selectionArgs = arrayOf(str)
        db.delete(TABLE_NAME, selection, selectionArgs)
        db.close()
    }

    fun clear(){
        val db = readableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}