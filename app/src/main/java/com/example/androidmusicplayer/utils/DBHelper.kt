package com.example.androidmusicplayer.utils
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.androidmusicplayer.struct.Play
import com.example.androidmusicplayer.struct.PlayList

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "mydatabase.db"
        private const val PLAY_LIST = "play_list_base"
        private const val SONG_IN_LIST = "song_in_list_base"
        private const val RECENT_PLAYED = "recent_played_base"
        private const val ID = "id"
        private const val NAME = "name"
        private const val COMMENT = "comment"
        private const val TIMESTAMP = "timestamp"
        private const val AUTHOR = "author"
        private const val FILENAME = "filename"
        private const val PATH = "path"
        private const val LIST_ID = "list_id"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        var createTableQuery = "CREATE TABLE $PLAY_LIST (id INTEGER PRIMARY KEY, name TEXT, comment TEXT, timestamp TEXT)"
        db?.execSQL(createTableQuery)
        createTableQuery = "CREATE TABLE $SONG_IN_LIST (id INTEGER PRIMARY KEY, name TEXT, author TEXT, filename TEXT, path TEXT, list_id INTEGER)"
        db?.execSQL(createTableQuery)
        createTableQuery = "CREATE TABLE $RECENT_PLAYED (id INTEGER PRIMARY KEY, name TEXT, author TEXT, filename TEXT, path TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var dropTableQuery = "DROP TABLE IF EXISTS $PLAY_LIST"
        db?.execSQL(dropTableQuery)
        dropTableQuery = "DROP TABLE IF EXISTS $SONG_IN_LIST"
        db?.execSQL(dropTableQuery)
        dropTableQuery = "DROP TABLE IF EXISTS $RECENT_PLAYED"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    private fun insertData(values: ContentValues, database: String) {
        val db = writableDatabase
        db.insert(database, null, values)
        db.close()
    }

    fun insertSongList(list: PlayList){
        val value = ContentValues()
        value.put("name", list.name)
        value.put("comment", list.comment)
        value.put("timestamp", list.timeStamp)
        insertData(value, PLAY_LIST)
    }

    fun insertSongInList(play: Play, listName: String){
        val value = ContentValues()
        value.put("name", play.name)
        value.put("author", play.author)
        value.put("filename", play.fileName)
        value.put("path", play.path)
        value.put("list_id", getListId(listName))
        insertData(value, SONG_IN_LIST)
    }

    fun insertSongInRecent(play: Play){
        val value = ContentValues()
        value.put("name", play.name)
        value.put("author", play.author)
        value.put("filename", play.fileName)
        value.put("path", play.path)
        insertData(value, RECENT_PLAYED)
    }

    fun removeSongList(listName: String){
        val db = readableDatabase
        val selection = "name = ?"
        val selectionArgs = arrayOf(listName)
        db.delete(PLAY_LIST, selection, selectionArgs)
        db.close()
    }

    fun removeSongInList(playName: String, listName: String){
        val db = readableDatabase
        val selection = "name = ? AND list_id = ?"
        val selectionArgs = arrayOf(playName, listName)
        db.delete(SONG_IN_LIST, selection, selectionArgs)
        db.close()
    }

    @SuppressLint("Range")
    fun getListId(name: String): Int {
        val db = readableDatabase
        val tableName = PLAY_LIST
        val columns = arrayOf("id", "name", "comment", "timestamp")
        val selection = "name = $name"
        val selectionArgs = null
        val groupBy = null
        val having = null
        val orderBy = null

        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy)

        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                cursor.close()
                db.close()
                return id
            }
        }
        cursor?.close()
        db.close()
        return -1
    }

    @SuppressLint("Range")
    fun getPlayLists(): ArrayList<PlayList>{
        val data = ArrayList<PlayList>()
        val db = readableDatabase

        val tableName = PLAY_LIST
        val columns = arrayOf("id", "name", "comment", "timestamp")
        val selection = null
        val selectionArgs = null
        val groupBy = null
        val having = null
        val orderBy = null

        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy)

        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val name = it.getString(it.getColumnIndex("name"))
                val comment = it.getString(it.getColumnIndex("comment"))
                val timestamp = it.getString(it.getColumnIndex("timestamp"))
                val list = PlayList(name, -1)
                list.comment = comment
                list.timeStamp = timestamp
                data.add(list)
            }
        }

        cursor?.close()
        db.close()

        return data
    }

    @SuppressLint("Range")
    fun getListPlays(listName: String): ArrayList<Play>{
        val data = ArrayList<Play>()
        val db = readableDatabase
        val listId = getListId(listName)

        val tableName = SONG_IN_LIST
        val columns = arrayOf("id", "name", "author", "filename", "path")
        var selection: String? = "list_id = $listId"
        if(listId == -1)
            selection = null
        val selectionArgs = null
        val groupBy = null
        val having = null
        val orderBy = null

        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy)

        cursor?.let {
            while (it.moveToNext()) {
                val name = it.getString(it.getColumnIndex("name"))
                val author = it.getString(it.getColumnIndex("author"))
                val filename = it.getString(it.getColumnIndex("filename"))
                val path = it.getString(it.getColumnIndex("path"))
                val play = Play(name, author)
                play.fileName = filename
                play.path = path
                data.add(play)
            }
        }

        cursor?.close()
        db.close()
        return data
    }

    @SuppressLint("Range")
    fun getRecentPlayed(): ArrayList<Play>{
        val data = ArrayList<Play>()
        val db = readableDatabase

        val tableName = RECENT_PLAYED
        val columns = arrayOf("id", "name", "author", "filename", "path")
        val selection = null
        val selectionArgs = null
        val groupBy = null
        val having = null
        val orderBy = "id DESC"
        val limit = "100"

        val cursor: Cursor? = db.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit)
        cursor?.let {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex("id"))
                val name = it.getString(it.getColumnIndex("name"))
                val author = it.getString(it.getColumnIndex("author"))
                val filename = it.getString(it.getColumnIndex("filename"))
                val path = it.getString(it.getColumnIndex("path"))
                val play = Play(name, author)
                play.fileName = filename
                play.path = path
                data.add(play)
            }
        }

        cursor?.close()
        db.close()

        return data
    }

}
