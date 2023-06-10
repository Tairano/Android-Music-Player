package com.example.androidmusicplayer.struct

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.util.Log
import java.io.File

class LocalFileManager {
    val list = PlayList("localMusic")
    val fileList = ArrayList<BasicList>()
    val singerList = ArrayList<BasicList>()

    private lateinit var assetManager : AssetManager

    fun searchLoadMusic(context: Context){
        assetManager = context.assets
        val paths = assetManager.list("")
        if (paths != null) {
            for(i in paths){
                searchFile(i)
            }
        }
    }

    private fun searchFile(path: String){
        val paths = assetManager.list(path)
        val bList = BasicList(path.split("/").last(), path)
        var check = false
        if (paths != null) {
            for(i in paths){
                val file = File("$path/$i")
                if(file.extension == "mp3"){
                    val (part) = i.split(".mp3")
                    val params = part.split('-')
                    var parm1 = params[0]
                    var parm2 = ""
                    if( params.size > 1){
                        parm2 = parm1
                        parm1 = params[1]
                    }
                    val play = Play(parm1,parm2)
                    play.path = "$path/$i"
                    play.fileName = i
                    play.bitMap = getBitMap(file.path)
                    bList.add(play)
                    list.add(play)
                    if( params.size > 1){
                        putInSinger(parm2).add(play)
                    }
                    check = true
                }
                searchFile("$path/$i")
            }
        }
        if(check){
            fileList.add(bList)
        }
    }

    private fun getBitMap(path: String): Bitmap? {
        val assetFileDescriptor = assetManager.openFd(path)
        val assetFilePath = assetFileDescriptor.fileDescriptor
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(assetFilePath)
        val embeddedArtwork = retriever.embeddedPicture
        return if (embeddedArtwork != null) {
            val bitmap = BitmapFactory.decodeByteArray(embeddedArtwork, 0, embeddedArtwork.size)
            Log.d("what?", "fuck")
            retriever.release()
            bitmap
        } else {
            retriever.release()
            null
        }
    }

    private fun putInSinger(author: String): BasicList{
        for(i in singerList){
            if(i.str == author){
                return i
            }
        }
        singerList.add(BasicList(author, ""))
        return singerList.last()
    }
}