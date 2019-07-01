package com.rathana.photo_book.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.ActivityComponent
import com.rathana.photo_book.base.BaseActivity
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.ui.home.fragment.BookmarkFragment
import com.rathana.photo_book.ui.home.fragment.HomeFragment
import com.rathana.photo_book.ui.home.fragment.SettingFragment
import com.rathana.photo_book.ui.home.mvp.HomeMVP
import com.rathana.photo_book.ui.home.mvp.HomePresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(){

    //@Inject lateinit var homePresenter: HomePresenter
    internal lateinit var homeFragment: HomeFragment
    internal lateinit var bookmarkFragment: BookmarkFragment
    internal lateinit var settingFragment: SettingFragment

    override fun onInject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }
    override fun setLayout(): Int =R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI(savedInstanceState)
    }

    fun initUI(savedInstanceState: Bundle?){

        homeFragment=HomeFragment.getInstance()
        bookmarkFragment=BookmarkFragment.getInstance()
        settingFragment= SettingFragment.getInstance()
        val fragments= mutableListOf<Fragment>(homeFragment,bookmarkFragment,settingFragment)

        addFragments(fragments,R.id.container,0)
        bottomNav?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.btnPhotoCollection->{
                    switchFragment(HOME_FRAGMENT ,fragment = fragments[0])
                    true
                }
                R.id.btnBookmark->{
                    switchFragment(BOOKMARK_FRAGMENT,fragment = fragments[1])
                    true
                }
                R.id.btnSetting->{
                    switchFragment(SETTING_FRAGMENT,fragment = fragments[2])
                    true
                }
                else -> false
            }
        }
    }


    /**
     * ===============
     *BLOCK DATA RESPONSE FROM SERVICES
     * ===============
     */

}
