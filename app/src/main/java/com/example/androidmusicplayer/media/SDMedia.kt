package com.example.androidmusicplayer.media

import android.content.Context
import android.provider.MediaStore
import com.example.androidmusicplayer.db_controll.LocalPlayDbHelper

fun getAllMp3Files(context: Context) {
    val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
    val projection = arrayOf(
        MediaStore.Audio.Media.TITLE,
        MediaStore.Audio.Media.ARTIST,
        MediaStore.Audio.Media.ALBUM,
        MediaStore.Audio.Media.DATA
    )
    val sortOrder = "${MediaStore.Audio.Media.TITLE} ASC"

    val cursor = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        null,
        sortOrder
    )

    val helper = LocalPlayDbHelper(context)
    helper.clear()

    cursor?.use { c ->

        val pathColumn = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        while (c.moveToNext()) {
            val path = c.getString(pathColumn)

            val folder = getLastSegmentBeforeLastSlash(path)

            val play = getMp3Metadata(path)
            if(play != null)
                helper.insert(play,folder)
        }
    }
}

fun getLastSegmentBeforeLastSlash(input: String): String {
    val lastSlashIndex = input.lastIndexOf('/')
    if (lastSlashIndex >= 0) {
        return input.substring(0, lastSlashIndex)
    }
    return input
}

fun getLastSegmentAfterLastSlash(input: String): String {
    return input.substringAfterLast("/")
}

// 示例用法
//val context: Context = ... // 获取上下文
//getAllMp3Files(context)