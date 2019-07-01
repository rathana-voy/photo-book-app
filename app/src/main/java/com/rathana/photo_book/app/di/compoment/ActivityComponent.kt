package com.rathana.photo_book.app.di.compoment

import android.app.Application
import android.content.Context
import com.rathana.photo_book.app.data.local.dao.PhotoBookDatabase
import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.app.di.module.ActivityModule
import com.rathana.photo_book.app.di.module.DataModule
import com.rathana.photo_book.app.di.module.MvpModule
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.app.di.scope.ActivityScope
import com.rathana.photo_book.ui.detail.DetailActivity
import com.rathana.photo_book.ui.home.MainActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named

@Component(dependencies = [AppComponent::class],modules = [ActivityModule::class,DataModule::class,MvpModule::class])
@ActivityScope
interface ActivityComponent {

    fun application(): Application
    fun context(): Context
    fun inject(activity: MainActivity)
    fun inject(activity: DetailActivity)

    //provide dependency from App Module
    @Named(NetworkModule.NO_AUTH_TOKEN)
    fun retrofit():Retrofit

    @Named(NetworkModule.AUTH_TOKEN)
    fun retrofitIncludeAuthToken():Retrofit

    //provide service from Data Module
    fun photoService():PhotoService
    fun photoBookDatabase(): PhotoBookDatabase

    //provide MVP module
    //fun homeInteractor(): HomeMVP.Interactor
}