package com.rathana.photo_book.app.di.compoment

import android.content.Context
import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.app.di.module.ActivityModule
import com.rathana.photo_book.app.di.module.DataModule
import com.rathana.photo_book.app.di.module.MpvModule
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.app.di.scope.ActivityScope
import com.rathana.photo_book.ui.home.MainActivity
import com.rathana.photo_book.ui.home.mvp.HomeMVP
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named

@Component(dependencies = [AppComponent::class],modules = [ActivityModule::class,DataModule::class,MpvModule::class])
@ActivityScope
interface ActivityComponent {

    fun context():Context
    fun inject(activity: MainActivity)


    //provide dependency from App Module
    @Named(NetworkModule.NO_AUTH_TOKEN)
    fun retrofit():Retrofit

    @Named(NetworkModule.AUTH_TOKEN)
    fun retrofitIncludeAuthToken():Retrofit

    //provide service from Data Module
    fun photoService():PhotoService

    //provide MVP module
    //fun homeInteractor(): HomeMVP.Interactor
}