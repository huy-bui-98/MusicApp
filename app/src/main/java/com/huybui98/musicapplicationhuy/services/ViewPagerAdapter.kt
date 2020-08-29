package com.huybui98.musicapplicationhuy.services

import android.util.Log.d
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.huybui98.musicapplicationhuy.fragments.PlayerFragment
import com.huybui98.musicapplicationhuy.fragments.MusicFragment

/**
 * Created by huy-bui-98 on 08/24/20
 * This is class for adapter of view pager in music activity
 */

class ViewPagerAdapter(
    fm: FragmentManager
) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        private const val NUMBER_OF_PAGER = 2
    }

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            d("adapter", "online")
            MusicFragment.newInstance()
        } else {
            PlayerFragment.newInstance()
        }

    }
    override fun getCount() = NUMBER_OF_PAGER
}
