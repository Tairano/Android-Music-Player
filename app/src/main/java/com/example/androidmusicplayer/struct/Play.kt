package com.example.androidmusicplayer.struct

import java.io.Serializable

class Play(var name: String, var author: String): Serializable {
    lateinit var fileName : String
    lateinit var path : String
    var playLocation : Int = 0
}