package com.example.androidmusicplayer.struct

import java.io.Serializable

class Play(var title: String, var artist: String?, var album: String?, var path: String?, var bitmap: ByteArray?): Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Play

        if (title != other.title) return false
        if (artist != other.artist) return false
        if (album != other.album) return false
        if (path != other.path) return false
        if (bitmap != null) {
            if (other.bitmap == null) return false
            if (!bitmap.contentEquals(other.bitmap)) return false
        } else if (other.bitmap != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + (artist?.hashCode() ?: 0)
        result = 31 * result + (album?.hashCode() ?: 0)
        result = 31 * result + (path?.hashCode() ?: 0)
        result = 31 * result + (bitmap?.contentHashCode() ?: 0)
        return result
    }
}