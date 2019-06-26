package com.rathana.photo_book.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rathana.photo_book.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeFragment : Fragment() {

    companion object{
        fun getInstance(): HomeFragment= HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_home,container,false)

        return view;
    }
}