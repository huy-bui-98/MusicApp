package com.huybui98.musicapplicationhuy.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.models.Song
import kotlinx.android.synthetic.main.fragment_1.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class FragmentOne : Fragment() {

    companion object {
        internal fun newInstance() = FragmentOne()
    }

    private lateinit var viewModel: SharedViewModelTest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProvider(activity as TestActivity).get(SharedViewModelTest::class.java)
        btnF1?.setOnClickListener {
            var list = mutableListOf<Song>()
            var count = 0
            for(i in 1..5){
                count++
                val text = edtF1?.text.toString() + " $count"
                val song = Song(nameSong = text)
                list.add(song)
            }
            viewModel.selectedItem(list)
        }
    }
}
