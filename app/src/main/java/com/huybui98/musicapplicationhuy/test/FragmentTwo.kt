package com.huybui98.musicapplicationhuy.test

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.huybui98.musicapplicationhuy.R
import com.huybui98.musicapplicationhuy.models.Song
import kotlinx.android.synthetic.main.fragment_2.*

/**
 * Created by huy-bui-98 on 08/21/20
 * This is fragment class for splash fragment of music application
 */

class FragmentTwo : Fragment() {

    companion object {
        internal fun newInstance() = FragmentTwo()
    }

    private lateinit var viewModel: SharedViewModelTest

    private var songLists = mutableListOf<Song>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProvider(activity as TestActivity).get(SharedViewModelTest::class.java)

        btnF2?.setOnClickListener {
            viewModel.selected.value?.forEach {
                d("XXXX","name : " + it.nameSong.toString())
            }
        }
    }

}
