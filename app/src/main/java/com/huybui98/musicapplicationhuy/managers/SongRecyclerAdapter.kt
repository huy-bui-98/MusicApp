package com.huybui98.musicapplicationhuy.managers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.models.Song
import kotlinx.android.synthetic.main.item_recycler_song.view.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is adapter class for musics recycler view
 */

class SongRecyclerAdapter(private val songList: List<Song>) :
    RecyclerView.Adapter<SongRecyclerAdapter.ItemViewHolder>() {

    internal var onItemClick: (Int) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recycler_song, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun getItemCount() = songList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var iconPlayer: ImageView = itemView.btnPlayItem
        private var nameSong: TextView = itemView.tvNameSongItem
        private var nameSinger: TextView = itemView.tvNameSingerItem
        private var lengthSong: TextView = itemView.tvLengthSongItem

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(adapterPosition)
            }
        }

        fun bindData() {
            songList[adapterPosition].let { song ->
                nameSong.isSelected = true
                nameSong.text = song.nameSong
                nameSinger.text = song.singer
                lengthSong.text = song.duration
                iconPlayer.setImageBitmap(song.getPicture(itemView.context))
            }
        }
    }
}
