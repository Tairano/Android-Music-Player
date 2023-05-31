package com.example.androidmusicplayer.struct

class Album (val name: String, val author: String, val albumPictureId: Int, val timeStamp: String): BasicList(name, author, imgId = albumPictureId) {
}