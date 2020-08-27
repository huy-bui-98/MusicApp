package com.huybui98.musicapplicationhuy.notification

/**
 * Asian Tech Co., Ltd.
 * Created by at-huybui on 08/25/20
 * This is interface for player click control
 */

interface ClickPlayable {
    fun onMusicPrevious()
    fun onMusicResume()
    fun onMusicPause()
    fun onMusicNext()
    fun onMusicStart()
}
