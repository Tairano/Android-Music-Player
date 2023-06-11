package com.example.androidmusicplayer.media

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.example.androidmusicplayer.struct.Play

const val TAG = "media_analysis"

fun getMp3Metadata(filePath: String): Play? {
    val retriever = MediaMetadataRetriever()

    try {
        retriever.setDataSource(filePath)

        val title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE)
        val artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)
        val album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)
        val embeddedArt = retriever.embeddedPicture

        val play = title?.let { Play(it,artist,album,filePath,embeddedArt) }
        return play
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        retriever.release()
    }
    return null
}

fun byteArrayToBitmap(byteArray: ByteArray?): Bitmap? {
    return if(byteArray != null)
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    else
        null
}
// 示例用法
//val filePath: String = ... // MP3 文件路径
//getMp3Metadata(filePath)

fun drawableToBitmap(context: Context, id: Int): Bitmap {
    val resources = context.resources
    return BitmapFactory.decodeResource(resources, id)
}