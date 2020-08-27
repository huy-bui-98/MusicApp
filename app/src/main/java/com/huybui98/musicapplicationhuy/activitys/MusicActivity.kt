package com.huybui98.musicapplicationhuy.activitys

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.fragments.SplashFragment
import com.huybui98.musicapplicationhuy.models.Song
import com.huybui98.musicapplicationhuy.services.AudioService
import com.huybui98.musicapplicationhuy.services.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_music.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is activity class for main activity of music application
 */

class MusicActivity : AppCompatActivity() {

    companion object {
        var service = AudioService()
        var svc = Intent()
        var bound = false
        var connection = object : ServiceConnection {
            override fun onServiceDisconnected(p0: ComponentName?) {
                bound = false
            }

            override fun onServiceConnected(component: ComponentName?, iBinder: IBinder?) {
                val binder = iBinder as AudioService.LocalBinder
                service = binder.getService()
                bound = true
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        setColorStatusBar()
        handleReplaceFragment(SplashFragment.newInstance(), false)
        svc = Intent(this, service::class.java)
        bindService(svc, connection, Context.BIND_AUTO_CREATE)
        startService(svc)
    }

    override fun onDestroy() {
        unbindService(connection)
        bound = false
        super.onDestroy()
    }

    internal fun initViewPager(songLists: MutableList<Song>) {
        val viewPagerAdapter by lazy { ViewPagerAdapter(supportFragmentManager, songLists) }
        containerViewPager?.apply {
            adapter = viewPagerAdapter
        }
    }

    private fun handleReplaceFragment(
        fragment: Fragment,
        isBackStack: Boolean = false,
        nameBackStack: String = ""
    ) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.containerMain, fragment)
        if (isBackStack) {
            fragmentTransaction.addToBackStack(nameBackStack)
        }
        fragmentTransaction.commit()
    }

    private fun setColorStatusBar() {
        this.apply {
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.w9_status_bar)
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility.and(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv())
        }
    }
}
