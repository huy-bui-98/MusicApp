package com.huybui98.musicapplicationhuy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.models.Song
import com.huybui98.musicapplicationhuy.services.AudioService
import kotlinx.android.synthetic.main.w9_fragment_music.*
import kotlinx.android.synthetic.main.w9_fragment_player.*

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/23/20
 * This is fragment class for splash fragment of player application
 */

class PlayerFragment : Fragment() {

    companion object {
        internal fun newInstance(lists: MutableList<Song>) = PlayerFragment().apply {
            lists.toCollection(songLists)
        }
        private const val DEFAULT_DURATION = "00:00"
    }

    private var songLists = mutableListOf<Song>()
    private val service = MusicActivity.service
    private var isUpdateSeekBar = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w9_fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        initPlayerView()
        initPlayerListener()
    }

    private fun initPlayerListener() {
        initPlayerClickListener()
        initServiceListener()
        initHandleSeekBar()
    }

    private fun initPlayerClickListener() {
        listenerClickButtonControl()
        listenerClickButtonRandomAndLoop()
    }

    private fun initPlayerView() {
        tvNameSong_Player.isSelected = true // set auto run text view
        service.songPlaying?.let { song ->
            tvNameSong_Player?.text = song.nameSong
            tvSinger_Player?.text = song.singer
            imgDvd_Player?.setImageBitmap(song.getPicture(requireContext(), false))
            if (service.audioPlayer.isPlaying) {
                btnPlay_Player?.setImageResource(R.drawable.ic_pause_button)
                imgDvd_Player?.startAnim()
            } else {
                imgDvd_Player?.setImageResource(R.drawable.ic_play_button)
                imgDvd_Music?.endAnim()
            }
        }
    }

    private fun initHandleSeekBar() {
        tvDuration_Player?.text = DEFAULT_DURATION
        tvCurrent_Player?.text = DEFAULT_DURATION
        if (service.audioPlayer.isPlaying) {
            service.audioPlayer.duration.let { duration ->
                tvDuration_Player?.text = Song().convertDuration(duration.toString())
                seekBar?.max = duration
            }
            service.songPlaying?.let { song ->
                tvDuration_Player?.text = song.duration
            }
            seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    val current = Song().convertDuration(p1.toString())
                    val duration = Song().convertDuration(service.audioPlayer.duration.toString())
                    val text = "$current | $duration"
                    tvUpdateCurrent_Player?.text = text
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                    tvUpdateCurrent_Player.visibility = View.VISIBLE
                    isUpdateSeekBar = true
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar?.let {
                        service.audioPlayer.seekTo(seekBar.progress)
                    }
                    tvUpdateCurrent_Player.visibility = View.INVISIBLE
                    isUpdateSeekBar = false
                }
            })
        }
    }

    private fun initServiceListener() {
        service.onUpdateCurrentPosition = {
            if (service.audioPlayer.isPlaying) {
                tvCurrent_Player?.text = Song().convertDuration(it.toString())
            }
            if (!isUpdateSeekBar) {
                seekBar?.progress = it
            }
        }

        service.onPlayer = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        tvNameSong_Player?.text = song.nameSong
                        tvSinger_Player?.text = song.singer
                        btnPlay_Player?.setImageResource(R.drawable.ic_pause_button)
                        val bitmap = song.getPicture(requireContext(), false)
                        imgDvd_Player?.setImageBitmap(bitmap)
                        imgDvd_Player?.startAnim()
                        initHandleSeekBar()
                    }
                }
                AudioService.StatePlayer.PAUSE -> {
                    btnPlay_Player?.setImageResource(R.drawable.ic_play_button)
                    imgDvd_Player?.pauseAnim()
                }
                AudioService.StatePlayer.RESUME -> {
                    btnPlay_Player?.setImageResource(R.drawable.ic_pause_button)
                    imgDvd_Player?.resumeAnim()

                }
            }
        }
    }

    private fun listenerClickButtonControl() {
        btnPlay_Player?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                if (service.audioPlayer.isPlaying) {
                    service.onMusicPause()
                } else {
                    service.onMusicResume()
                }
            }
        }
        btnPrevious_Player?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicPrevious()
            }
        }
        btnNext_Player?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicNext()
            }
        }
    }

    private fun listenerClickButtonRandomAndLoop() {
        btnReplay_Player?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                if (service.audioPlayer.isLooping) {
                    service.audioPlayer.isLooping = false
                    btnReplay_Player?.setImageResource(R.drawable.ic_replay_off)
                } else {
                    service.audioPlayer.isLooping = true
                    btnReplay_Player?.setImageResource(R.drawable.ic_replay_on)
                }
            }
        }
        btnRandom_Player?.setOnClickListener {
            if (service.isRandom) {
                btnRandom_Player.setImageResource(R.drawable.ic_random_off)
                service.isRandom = false
                service.onShuffleMusic(songLists)
            } else {
                btnRandom_Player.setImageResource(R.drawable.ic_random_on)
                service.isRandom = true
                service.onShuffleMusic(songLists)
            }
        }
    }
}
