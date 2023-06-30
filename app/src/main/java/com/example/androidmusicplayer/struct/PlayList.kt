package com.example.androidmusicplayer.struct

import android.graphics.Bitmap
import java.io.Serializable

class PlayList (var name: String, var user : String?, var comment: String?, var bitmap : ByteArray?, var size: Int, var id : Int): Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PlayList

        if (name != other.name) return false
        if (user != other.user) return false
        if (comment != other.comment) return false
        if (bitmap != null) {
            if (other.bitmap == null) return false
            if (!bitmap.contentEquals(other.bitmap)) return false
        } else if (other.bitmap != null) return false
        if (size != other.size) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (user?.hashCode() ?: 0)
        result = 31 * result + (comment?.hashCode() ?: 0)
        result = 31 * result + (bitmap?.contentHashCode() ?: 0)
        result = 31 * result + size
        result = 31 * result + id
        return result
    }
}