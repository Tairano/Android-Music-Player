package com.example.androidmusicplayer.struct

import android.content.Context
import android.content.res.AssetManager
import com.example.androidmusicplayer.R
import java.io.File
import kotlin.math.sin

class LocalFileManager {
    val list = PlayList("localMusic",R.drawable.logo)
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
        val bList = BasicList(path.split("/").last(), path, R.drawable.folder)
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

    private fun putInSinger(author: String): BasicList{
        for(i in singerList){
            if(i.str == author){
                return i
            }
        }
        singerList.add(BasicList(author, "",R.drawable.logo))
        return singerList.last()
    }
}