package com.huybui98.musicapplicationhuy.fragments

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.models.SharedViewModel
import com.huybui98.musicapplicationhuy.models.Song
import com.huybui98.musicapplicationhuy.services.AudioService
import kotlinx.android.synthetic.main.activity_music.*
import kotlinx.android.synthetic.main.fragment_test.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class MusicFragment : Fragment() {

    companion object {
        internal fun newInstance() = MusicFragment()
    }

    private lateinit var viewModel: SharedViewModel
    private var songLists = mutableListOf<Song>()
    private val service = MusicActivity.service

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity as MusicActivity).get(SharedViewModel::class.java)
        initData()
        initView()
        initAudioService()
        bindOfflineFragment()
        initListenerMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        service.isOpenApp = false  // clear state is open app
    }

    internal fun bindOfflineFragment() {
        val fragment = MusicOfflineFragment.newInstance()
        fragment.onItemOfflineClick = { position ->
            service.songPosition = position
            service.songLists.forEach {
                d("ofline", it.nameSong.toString())
            }
            service.onMusicStart()
            (activity as MusicActivity).viewPager.setCurrentItem(1, true)
        }
        (activity as MusicActivity).viewPager.setCurrentItem(0, true)
        handleReplaceFragment(fragment)
    }

    internal fun bindOnlineFragment() {
        val fragment = MusicOnlineFragment.newInstance()
        (activity as MusicActivity).viewPager.setCurrentItem(0, true)
        handleReplaceFragment(fragment)
    }

    internal fun handleReplaceFragment(
        fragment: Fragment,
        isBackStack: Boolean = false,
        nameBackStack: String = ""
    ) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.FrameMusicFragment, fragment)
        if (isBackStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun initData() {
        viewModel.listOffline.value?.toCollection(songLists)
    }

    private fun initView() {
        initPlayerBar()
        initSystemView()
    }

    private fun initPlayerBar() {
        initPlayerBarClickListener()
        initPlayerBarView()
    }

    private fun initPlayerBarView() {
        tvSongName_Music.isSelected = true // set auto run text view
        service.songPlaying?.let { song ->
            tvSongName_Music?.text = song.nameSong
            tvSinger_Music?.text = song.singer
            val bitmap = song.getPicture(requireContext())
            imgDvd_Music?.setImageBitmap(bitmap)
            if (service.audioPlayer.isPlaying) {
                btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                imgDvd_Music?.startAnim()
            } else {
                btnPlay_Music?.setImageResource(R.drawable.ic_play_button)
                imgDvd_Music?.endAnim()
            }
        }
    }

    private fun initPlayerBarClickListener() {
        btnPlay_Music?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                if (service.audioPlayer.isPlaying) {
                    service.onMusicPause()
                } else {
                    service.onMusicResume()
                }
            }
        }
        btnPrevious_Music?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicPrevious()
            }
        }
        btnNext_Music?.setOnClickListener {
            if (service.audioPlayer.isPlaying) {
                service.onMusicNext()
            }
        }

        rlPlayerBar?.setOnClickListener {
            (activity as MusicActivity).viewPager.setCurrentItem(1, true)
        }
    }

    private fun initSystemView() {
        btnHome_Music?.setOnClickListener {
            (activity as MusicActivity).openNavigation()
        }
    }

    private fun initAudioService() {
        if (service.songLists.size < 1) {
            songLists.toCollection(service.songLists)
        }
        service.isOpenApp = true // set status is open app in onCreate
        //init listener from service at here
        service.onPlayerBar = { statePlayer ->
            when (statePlayer) {
                AudioService.StatePlayer.START -> {
                    service.songPlaying?.let { song ->
                        val bitmap = song.getPicture(requireContext())
                        btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                        imgDvd_Music?.setImageBitmap(bitmap)
                        imgDvd_Music?.startAnim()
                        tvSongName_Music?.text = song.nameSong
                        tvSinger_Music?.text = song.singer
                    }
                }
                AudioService.StatePlayer.RESUME -> {
                    imgDvd_Music?.resumeAnim()
                    btnPlay_Music?.setImageResource(R.drawable.ic_pause_button)
                }
                AudioService.StatePlayer.PAUSE -> {
                    imgDvd_Music?.pauseAnim()
                    btnPlay_Music?.setImageResource(R.drawable.ic_play_button)
                }
            }
        }
    }

    private fun initListenerMenu(){
        viewModel.displayFragment.observe(viewLifecycleOwner, Observer<SharedViewModel.MenuNavigation> { display ->
            when(display){
                SharedViewModel.MenuNavigation.OFFLINE ->{
                    bindOfflineFragment()
                }
                SharedViewModel.MenuNavigation.ONLINE->{
                    bindOnlineFragment()
                }
                else ->{
                }
            }
        })
    }
}
