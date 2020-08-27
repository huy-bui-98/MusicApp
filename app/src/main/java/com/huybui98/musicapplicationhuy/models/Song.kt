package com.huybui98.musicapplicationhuy.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.provider.BaseColumns
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.huybui98.musicapplicationhuy.R
import java.io.Serializable

/**
 * Created by huy-bui-98 on 08/21/20
 * This is data class for song item in musics recycler view
 */

@Entity(tableName = Song.TABLE_NAME)
data class Song(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID) var id: Long = 0,
    @ColumnInfo(name = NAME_SONG) var nameSong: String? = "null",
    @ColumnInfo(name = NAME_SINGER) var singer: String? = "null",
    @ColumnInfo(name = DURATION_SONG) var duration: String? = "00:00",
    @ColumnInfo(name = CONTENT_URI) var contentUri: String = "null"
) : Serializable {
    companion object {
        private const val COLUMN_ID = BaseColumns._ID
        internal const val TABLE_NAME = "villains"
        private const val NAME_SONG = "name_song"
        private const val NAME_SINGER = "name_singer"
        private const val DURATION_SONG = "length_song"
        private const val CONTENT_URI = "content_uri"
        private const val DEFAULT_NAME = "no name"
        private const val DEFAULT_DURATION = "00:00"
        private const val MINISECOUND = 1000
        private const val MINUTE = 60
    }

    fun getData(context: Context): Song {
        val songOld = this
        var songNew: Song
        MediaMetadataRetriever().apply {
            var duration = DEFAULT_DURATION
            var author = DEFAULT_NAME
            var name = songOld.nameSong
            setDataSource(context, Uri.parse(songOld.contentUri))
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.let {
                duration = convertDuration(it)
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST)?.let {
                author = it
            }
            extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM)?.let {
                name = it
            }
            songNew = Song(songOld.id, name, author, duration, songOld.contentUri)
        }
        return songNew
    }

    fun getPicture(
        context: Context,
        smallIcon: Boolean = true,
        notification: Boolean = false
    ): Bitmap? {
        var picture: Bitmap? = null
        MediaMetadataRetriever().apply {
            setDataSource(context, Uri.parse(this@Song.contentUri))
            embeddedPicture?.let {
                picture = BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        }
        if (picture == null && !notification) {
            picture = if (smallIcon) {
                ContextCompat.getDrawable(context, R.drawable.ic_dvd_player)?.toBitmap()
            } else {
                ContextCompat.getDrawable(context, R.drawable.img_dvd_player)?.toBitmap()
            }
        }
        return picture
    }

    fun convertDuration(duration: String?): String {
        var result = DEFAULT_DURATION
        duration?.let {
            val durationInt = it.toInt()
            val second = ((durationInt / MINISECOUND) % MINUTE).toString().padStart(2, '0')
            val minute = ((durationInt / MINISECOUND) / MINUTE).toString().padStart(2, '0')
            result = "$minute:$second"
        }
        return result
    }
}
