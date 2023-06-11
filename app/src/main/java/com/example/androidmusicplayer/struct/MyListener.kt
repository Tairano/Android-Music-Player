package com.example.androidmusicplayer.struct

import android.graphics.Bitmap
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.androidmusicplayer.R


val PLAY_STATUS = arrayOf(
    0 to R.drawable.play,
    1 to R.drawable.stop
)

val FAVOUR_STATUS = arrayOf(
    0 to R.drawable.favour1,
    0 to R.drawable.favour2
)

val PLAY_TYPE_STATUS = arrayOf(
    0 to R.drawable.one_recycle,
    1 to R.drawable.hole_recycle,
    2 to R.drawable.random
)

class MyListener {
    private val playButtonList = ArrayList<Button>()
    private val playTypeButtonList = ArrayList<Button>()
    private val favourButtonList = ArrayList<TextView>()
    private val nameList = ArrayList<TextView>()
    private val authorList = ArrayList<TextView>()
    private val bitmapList = ArrayList<ImageView>()

    var playStatus = 1
    var favourStatus = 0
    var playTypeStatus = 2
    var name = ""
    var author = ""
    var bitmap : Bitmap? = null

    fun refresh(){
        refreshPlayButton()
        refreshPlayTypeButton()
        refreshNameText()
        refreshAuthorText()
        refreshFavourButton()
        refreshBitmapView()
    }

    private fun refreshPlayButton(){
        for(i in playButtonList){
            i.setBackgroundResource(PLAY_STATUS[playStatus].second)
        }
    }

    private fun refreshPlayTypeButton(){
        for(i in playTypeButtonList){
            i.setBackgroundResource(PLAY_TYPE_STATUS[playTypeStatus].second)
        }
    }


    private fun refreshFavourButton(){
        for(i in favourButtonList){
            i.setBackgroundResource(FAVOUR_STATUS[favourStatus].second)
        }
    }

    private fun refreshNameText(){
        for(i in nameList){
            i.text = name
        }
    }

    private fun refreshBitmapView(){
        for(i in bitmapList){
            if(bitmap != null)
                i.setImageBitmap(bitmap)
            else
                i.setImageResource(R.drawable.album)
        }
    }

    private fun refreshAuthorText(){
        for(i in authorList){
            i.text = author
        }
    }

    fun addPlayButton(button: Button){
        playButtonList.add(button)
    }

    fun removePlayButton(button: Button){
        playButtonList.remove(button)
    }

    fun addPlayTypeButton(button: Button){
        playTypeButtonList.add(button)
    }

    fun removePlayTypeButton(button: Button){
        playTypeButtonList.remove(button)
    }

    fun addFavourButton(button: Button){
        favourButtonList.add(button)
    }

    fun removeFavourButton(button: Button){
        favourButtonList.remove(button)
    }

    fun addNameView(view: TextView){
        nameList.add(view)
    }

    fun removeNameView(view: TextView){
        nameList.remove(view)
    }

    fun addAuthorView(view: TextView){
        authorList.add(view)
    }

    fun removeAuthorView(view: TextView){
        authorList.remove(view)
    }

    fun addBitmapView(view: ImageView){
        bitmapList.add(view)
    }

    fun removeBitmapView(view: ImageView){
        bitmapList.remove(view)
    }
}