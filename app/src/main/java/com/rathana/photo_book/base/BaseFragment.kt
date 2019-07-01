package com.rathana.photo_book.base

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.DaggerFragmentComponent
import com.rathana.photo_book.app.di.compoment.FragmentComponent
import kotlinx.android.synthetic.main.toolbar_layout.*
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment(){

    lateinit var component: FragmentComponent
    var isFragmentRecreateView=false
    override fun onCreate(savedInstanceState: Bundle?) {
        component=DaggerFragmentComponent.builder()
            .activityComponent((activity as BaseActivity).activityComponent)
            .build()
        onInject(component)

        super.onCreate(savedInstanceState)
    }

    abstract fun onInject(component: FragmentComponent);

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    fun setTitle(title: String){
       toolbarTitle!!.text=title
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_option_menu,menu)
        super.onCreateOptionsMenu(menu, inflater);
    }
}

