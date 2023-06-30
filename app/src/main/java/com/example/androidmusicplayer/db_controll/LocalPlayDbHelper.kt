package com.example.androidmusicplayer.db_controll

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import com.example.androidmusicplayer.R
import com.example.androidmusicplayer.media.drawableToBitmap
import com.example.androidmusicplayer.struct.LocalStorage
import com.example.androidmusicplayer.struct.Play
import java.io.ByteArrayOutputStream

class LocalPlayDbHelper (val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 6
        private const val DATABASE_NAME = "local_play_base.db"

        const val TABLE_NAME = "local_play_base"

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

        const val COLUMN7 = "folder_name"
        const val COLUMN7_TYPE = "TEXT"

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

    fun insert(play: Play, pathName: String){
        val value = ContentValues()
        value.put(COLUMN2, play.title)
        value.put(COLUMN3, play.artist)
        value.put(COLUMN4, play.album)
        value.put(COLUMN5, play.path)
        value.put(COLUMN6, play.bitmap)
        value.put(COLUMN7, pathName)
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
    fun getByPath(path: String): Play? {
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6,
            COLUMN7
        )
        val selection = "$COLUMN5 = ?"
        val selectionArgs = arrayOf(path)
        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(COLUMN2))
                val artist = it.getString(it.getColumnIndex(COLUMN3))
                val album = it.getString(it.getColumnIndex(COLUMN4))
                val path = it.getString(it.getColumnIndex(COLUMN5))
                val bitmap = it.getBlob(it.getColumnIndex(COLUMN6))
                val play = Play(title,artist,album,path,bitmap)
                return play
            }
        }
        cursor?.close()
        db.close()
        return null
    }

    @SuppressLint("Range")
    fun queryByType(name: String, type: String): ArrayList<Play> {
        val list = ArrayList<Play>()
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6,
            COLUMN7
        )
        val selection = "$type = ?"
        val selectionArgs = arrayOf(name)
        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(COLUMN2))
                val artist = it.getString(it.getColumnIndex(COLUMN3))
                val album = it.getString(it.getColumnIndex(COLUMN4))
                val path = it.getString(it.getColumnIndex(COLUMN5))
                val bitmap = it.getBlob(it.getColumnIndex(COLUMN6))
                val play = Play(title,artist,album,path,bitmap)
                list.add(play)
            }
        }
        cursor?.close()
        db.close()
        return list
    }

    @SuppressLint("Range")
    fun query(str: String?): ArrayList<Play> {
        val list = ArrayList<Play>()
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6,
            COLUMN7
        )
        var selectionArgs : Array<String>? = null
        val selection =
            if(str != null){
                selectionArgs = arrayOf("%$str%")
                "$COLUMN2 like ?"
            }
            else null
        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                val title = it.getString(it.getColumnIndex(COLUMN2))
                val artist = it.getString(it.getColumnIndex(COLUMN3))
                val album = it.getString(it.getColumnIndex(COLUMN4))
                val path = it.getString(it.getColumnIndex(COLUMN5))
                val bitmap = it.getBlob(it.getColumnIndex(COLUMN6))
                val play = Play(title,artist,album,path,bitmap)
                list.add(play)
            }
        }
        cursor?.close()
        db.close()
        return list
    }

    @SuppressLint("Range")
    fun aggregate(str: String?): HashMap<String,ArrayList<Play>>{
        val list = HashMap<String,ArrayList<Play>>()
        if(str == null)
            return list
        val db = readableDatabase
        val tableName = TABLE_NAME
        val columns = arrayOf(
            COLUMN1,
            COLUMN2,
            COLUMN3,
            COLUMN4,
            COLUMN5,
            COLUMN6,
            COLUMN7
        )
        val orderBy = "$str ASC"
        val cursor: Cursor? = db.query(tableName, columns, null, null, null, null, str)
        var key : String = ""
        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val groupBy = it.getString(it.getColumnIndex(str)) ?: continue
                    if(key != groupBy){
                        key = groupBy
                        list[key] = ArrayList<Play>()
                    }
                    val title = it.getString(it.getColumnIndex(COLUMN2))
                    val artist = it.getString(it.getColumnIndex(COLUMN3))
                    val album = it.getString(it.getColumnIndex(COLUMN4))
                    val path = it.getString(it.getColumnIndex(COLUMN5))
                    val bitmap : ByteArray? = it.getBlob(it.getColumnIndex(COLUMN6))
                    val play = Play(title,artist,album,path,bitmap)
                    list[key]?.add(play)
                } while (cursor.moveToNext())
            }
        }
        cursor?.close()
        db.close()
        return list
    }

    fun queryColumnInArtist(): ArrayList<LocalStorage>{
        val hashMap : HashMap<String,ArrayList<Play>> = aggregate(COLUMN3)
        val storageList = ArrayList<LocalStorage>()
        for(i in hashMap){
            val localStorage = LocalStorage(i.key, i.value.first().artist, i.value.size, i.value.first().bitmap,COLUMN3)
            storageList.add(localStorage)
        }
        return storageList
    }

    fun queryColumnInAlbum(): ArrayList<LocalStorage>{
        val hashMap : HashMap<String,ArrayList<Play>> = aggregate(COLUMN4)
        val storageList = ArrayList<LocalStorage>()
        for(i in hashMap){
            val localStorage = LocalStorage(i.key, i.value.first().artist, i.value.size, i.value.first().bitmap,COLUMN4)
            storageList.add(localStorage)
        }
        return storageList
    }

    fun queryColumnInFolder(): ArrayList<LocalStorage>{
        val hashMap : HashMap<String,ArrayList<Play>> = aggregate(COLUMN7)
        val storageList = ArrayList<LocalStorage>()
        for(i in hashMap){
            val bitmap = drawableToBitmap(context, R.drawable.folder)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val localStorage = LocalStorage(i.key, i.key, i.value.size, stream.toByteArray(),COLUMN7)
            storageList.add(localStorage)
        }
        return storageList
    }

    fun clear(){
        val db = writableDatabase
        db.execSQL("DELETE FROM $TABLE_NAME")
        db.close()
    }
}

