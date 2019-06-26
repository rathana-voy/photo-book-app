package com.rathana.photo_book.ui.home

import android.os.Bundle
import android.util.Log
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

class MainActivity : BaseActivity() ,
HomeMVP.View<HomeMVP.Presenter>{

    @Inject lateinit var homePresenter: HomePresenter

    override fun onInject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI();
        homePresenter.setView(this)
        homePresenter.getPhotos(1,30)
    }

    fun initUI(){
        replaceFragment(R.id.container,HomeFragment.getInstance())
        bottomNav?.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.btnPhotoCollection->{
                    replaceFragment(R.id.container,HomeFragment.getInstance())
                    true
                }
                R.id.btnBookmark->{
                    replaceFragment(R.id.container,BookmarkFragment.getInstance())
                    true
                }
                R.id.btnSetting->{
                    replaceFragment(R.id.container,SettingFragment.getInstance())
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

    override fun onError(smg: String) {
        Log.e("data",""+smg)
    }

    override fun onHideLoading() {

    }

    override fun onShowLoading() {

    }

    override fun onPhotosResponse(data: MutableList<Photo>) {
        Log.e("data",""+data)
    }
}
