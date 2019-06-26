package com.rathana.photo_book.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rathana.photo_book.app.App
import com.rathana.photo_book.app.di.compoment.ActivityComponent
import com.rathana.photo_book.app.di.compoment.AppComponent
import com.rathana.photo_book.app.di.compoment.DaggerActivityComponent
import com.rathana.photo_book.app.di.module.ActivityModule

abstract class BaseActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent=DaggerActivityComponent.builder().appComponent((application as App).appComponent).build()
        this.onInject(activityComponent)
    }

    abstract fun onInject(activityComponent: ActivityComponent)

    protected fun replaceFragment(@IdRes container: Int,fragment: Fragment,tag:String? = ""){
        val transaction=supportFragmentManager.beginTransaction()
        transaction.replace(container,fragment,tag?:"")
        transaction.commit()
    }
}