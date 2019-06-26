package com.rathana.photo_book.app

import android.app.Application
import android.util.Log
import com.rathana.photo_book.app.di.compoment.AppComponent
import com.rathana.photo_book.app.di.compoment.DaggerAppComponent
import com.rathana.photo_book.app.di.module.AppModule
import com.rathana.photo_book.app.di.module.NetworkModule

class App : Application(){
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()

        appComponent=DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .build()
        appComponent.inject(this)

    }

}