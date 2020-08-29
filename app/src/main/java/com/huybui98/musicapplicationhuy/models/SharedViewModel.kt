package com.huybui98.musicapplicationhuy.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by huy-bui-98 on 08/21/20
 * This is ViewModel for activity
 */

class SharedViewModel : ViewModel() {
    var displayFragment = MutableLiveData<MenuNavigation>()
    var listOffline = MutableLiveData<MutableList<Song>>()
    var listOnline = MutableLiveData<MutableList<Song>>()

    fun setDisplay(display: MenuNavigation){
        displayFragment.value = display
    }

    fun setListOnline(list: MutableList<Song>) {
        listOnline.value = list
        // Log.d("YYYY", "name : " + selected.value.toString())
    }

    fun setListOffline(list: MutableList<Song>) {
        listOffline.value = list
    }

    enum class MenuNavigation { OFFLINE, ONLINE }
}
