package com.huybui98.musicapplicationhuy.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.managers.SongRecyclerAdapter
import com.huybui98.musicapplicationhuy.models.Song

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicOnlineFragment : Fragment() {

    companion object {
        internal fun newInstance() = MusicOnlineFragment()
    }

    private var songLists = mutableListOf<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ZZZZ", "[ online] onCreateView")
        return inflater.inflate(R.layout.fragment_music_online, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("ZZZZ", "[ online] onViewCreated")
    }

}
