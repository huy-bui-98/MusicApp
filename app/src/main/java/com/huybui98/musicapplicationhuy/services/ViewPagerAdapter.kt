package com.huybui98.musicapplicationhuy.services

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.huybui98.musicapplicationhuy.fragments.MusicFragment
import com.huybui98.musicapplicationhuy.fragments.PlayerFragment
import com.huybui98.musicapplicationhuy.models.Song

/**
 * Created by huy-bui-98 on 08/24/20
 * This is class for adapter of view pager in music activity
 */

class ViewPagerAdapter(
    fm: FragmentManager,
    private val songLists: MutableList<Song>
) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    companion object {
        private const val NUMBER_OF_PAGER = 2
    }

    override fun getItem(position: Int): Fragment {

        return if (position == 0) {
            MusicFragment.newInstance(songLists)
        } else {
            PlayerFragment.newInstance(songLists)
        }
    }
    override fun getCount() = NUMBER_OF_PAGER
}
