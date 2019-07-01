package com.rathana.photo_book.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.FragmentComponent
import com.rathana.photo_book.base.BaseActivity
import com.rathana.photo_book.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*

class SettingFragment : BaseFragment() {

    override fun onInject(component: FragmentComponent) {
        component.inject(this)
    }
    companion object{
        fun getInstance(): SettingFragment= SettingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =inflater.inflate(R.layout.fragment_setting,container,false)

        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(BaseActivity.bottomNavigationTitles[2])
    }
}