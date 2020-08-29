package com.huybui98.musicapplicationhuy.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.managers.SongRecyclerAdapter
import com.huybui98.musicapplicationhuy.models.SharedViewModel
import com.huybui98.musicapplicationhuy.models.Song
import kotlinx.android.synthetic.main.fragment_music_offline.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicOfflineFragment : Fragment() {

    companion object {
        internal fun newInstance() = MusicOfflineFragment()
    }

    internal var onItemOfflineClick: (Int) -> Unit = {}
    private lateinit var viewModel: SharedViewModel
    private var songLists = mutableListOf<Song>()
    private val songAdapter = SongRecyclerAdapter(songLists)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        d("ZZZZ", "[offline] onCreateView")
        return inflater.inflate(R.layout.fragment_music_offline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        d("ZZZZ", "[offline] onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity as MusicActivity).get(SharedViewModel::class.java)
        initData()
        initMusicRecycler()
    }

    private fun initData() {
        viewModel.listOffline.value?.toCollection(songLists)
    }

    private fun initMusicRecycler() {
        recyclerViewSongList?.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(requireContext())
            songAdapter.notifyDataSetChanged()
            setHasFixedSize(true)
        }

        songAdapter.onItemClick = { position ->
            onItemOfflineClick.invoke(position)
        }
    }
}
