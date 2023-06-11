package com.example.androidmusicplayer.db_controll

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import com.example.androidmusicplayer.media.byteArrayToBitmap
import com.example.androidmusicplayer.struct.Play
import java.io.ByteArrayOutputStream

class PlayDbHelper (context: Context) : DBHelper(context) {
    companion object {
        const val TABLE_NAME = "play_base"

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

        const val COLUMN7 = "list_id"
        const val COLUMN7_TYPE = "INTEGER"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN1 $COLUMN1_TYPE, " +
                "$COLUMN2 $COLUMN2_TYPE, " +
                "$COLUMN3 $COLUMN3_TYPE, " +
                "$COLUMN4 $COLUMN4_TYPE, " +
                "$COLUMN5 $COLUMN5_TYPE, " +
                "$COLUMN6 $COLUMN6_TYPE, " +
                "$COLUMN7 $COLUMN7_TYPE)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    @SuppressLint("Range")
    fun findIdByName(str: String): Int {
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6
        )
        val selection = "$COLUMN2 = $str"
        var id = -1
        val cursor: Cursor? = db.query(tableName, columns, selection, null, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                id = it.getInt(it.getColumnIndex(COLUMN1))
                break
            }
        }
        cursor?.close()
        db.close()
        return id
    }

    @SuppressLint("Range")
    fun queryByListId(id: Int): ArrayList<Play>{
        val list = ArrayList<Play>()
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6
        )
        val selection = "$COLUMN7 = $id"
        val cursor: Cursor? = db.query(tableName, columns, selection, null, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(COLUMN2))
                val artist = it.getString(it.getColumnIndex(COLUMN3))
                val album = it.getString(it.getColumnIndex(COLUMN4))
                val path = it.getString(it.getColumnIndex(COLUMN5))
                val bitmap = it.getBlob(it.getColumnIndex(COLUMN6))
                val play = Play(title,artist,album,path, bitmap)
                list.add(play)
            }
        }
        cursor?.close()
        db.close()
        return list
    }

    fun insert(play: Play, listName: String){
        val value = ContentValues()
        value.put(COLUMN2, play.title)
        value.put(COLUMN3, play.artist)
        value.put(COLUMN4, play.album)
        value.put(COLUMN5, play.path)
        value.put(COLUMN6, play.bitmap)
        value.put(COLUMN7, findIdByName(listName))
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