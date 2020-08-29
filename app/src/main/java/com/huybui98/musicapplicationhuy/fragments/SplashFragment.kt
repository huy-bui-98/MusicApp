package com.huybui98.musicapplicationhuy.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.CountDownTimer
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.activitys.MusicActivity
import com.huybui98.musicapplicationhuy.models.SharedViewModel
import com.huybui98.musicapplicationhuy.models.Song
import com.huybui98.musicapplicationhuy.services.ViewPagerAdapter
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class SplashFragment : Fragment() {

    companion object {
        internal fun newInstance() = SplashFragment()
        private const val PERMISSION_REQUEST_CODE = 200
        private const val VALUE_TIMER = 120000L
        private const val TICK_TIMER = 10L
        private const val PROGRESS_BAR_MAX_VALUE = 100
        private const val TICK_REQUEST_PERMISSION = 5
        private const val TICK_LOAD_DATA_MUSIC = 10
        private const val TIME_SET = "1.1.2000"
        private const val DATE_FORMAT = "dd.MM.yyyy"
    }

    private lateinit var viewModel: SharedViewModel
    var progressCount = 0
    var songLists = mutableListOf<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(activity as MusicActivity).get(SharedViewModel::class.java)
        handleProgressBar()
    }

    private fun handleProgressBar() {
        object : CountDownTimer(VALUE_TIMER, TICK_TIMER) {
            override fun onFinish() {
                (activity as MusicActivity).finish()
            }

            override fun onTick(p0: Long) {
                progressCount += 1
                when (progressCount) {
                    TICK_REQUEST_PERMISSION -> {
                        if (!isCheckStorePermission()) {
                            requestStorePermission()
                            progressCount = 0
                        }
                    }
                    TICK_LOAD_DATA_MUSIC -> {
                        Thread(Runnable {
                             scanSongInStore().toCollection(songLists)
                        }).start()
                    }
                    PROGRESS_BAR_MAX_VALUE -> {
                        viewModel.setListOffline(songLists)
                        (activity as MusicActivity).handleViewPager()
                        this.cancel()
                    }
                }
            }
        }.start()
    }

    private fun scanSongInStore(): MutableList<Song> {
        val songListsNew = mutableListOf<Song>()
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DISPLAY_NAME
        )
        val selection = "${MediaStore.Audio.Media.DATE_ADDED} >= ?"
        val selectionArgs = arrayOf(dateToTimestamp().toString())
        val sortOrder = "${MediaStore.Audio.Media.DATE_ADDED} DESC"

        requireContext().applicationContext.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)
            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id
                )
                val name = cursor.getString(nameColumn)
                val song =
                    Song(id, name, contentUri = contentUri.toString()).getData(requireContext())
                songListsNew.add(song)
            }
        }
        return songListsNew
    }

    private fun isCheckStorePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestStorePermission() {
        ActivityCompat.requestPermissions(
            requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_CODE
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun dateToTimestamp(): Long =
        SimpleDateFormat(DATE_FORMAT).let { formatter ->
            TimeUnit.MICROSECONDS.toSeconds(formatter.parse(TIME_SET)?.time ?: 0)
        }
}
