package com.huybui98.musicapplicationhuy.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.huybui98.musicapplicationhuy.models.Song

class SharedViewModelTest : ViewModel() {
    var selected = MutableLiveData<MutableList<Song>>()
    fun selectedItem(data: MutableList<Song>) {
        selected.value = data
           // Log.d("YYYY", "name : " + selected.value.toString())
    }

}