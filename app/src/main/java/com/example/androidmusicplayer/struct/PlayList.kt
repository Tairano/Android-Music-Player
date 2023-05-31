package com.example.androidmusicplayer.struct

class PlayList (val name: String, val imageId: Int): BasicList(name, "", imageId) {
    private val list = ArrayList<Play>()

    val timeStamp : String = "2023年5月28日"
    var comment : String = "希望能天天开心"

    fun searchToList(str: String): ArrayList<Play> {
        if(name.isEmpty())
            return list
        var list = ArrayList<Play>()
        for(i in 0 until this.list.size) {
            if(this.list[i].name.contains(str) || this.list[i].author.contains(str))
                list.add(this.list[i])
        }
        return list
    }

}