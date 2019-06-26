package com.rathana.photo_book.app.di.compoment

import android.app.Application
import android.content.Context
import com.rathana.photo_book.app.di.module.ActivityModule
import com.rathana.photo_book.app.di.module.AppModule
import com.rathana.photo_book.app.di.module.DataModule
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.app.di.scope.ApplicationScope
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.ui.home.mvp.HomeMVP
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named

@Component(modules = [AppModule::class,NetworkModule::class])
@ApplicationScope
interface AppComponent {

    fun context():Context

    fun inject(application: Application)

    @Named(NetworkModule.NO_AUTH_TOKEN)
    fun retrofit(): Retrofit

    @Named(NetworkModule.AUTH_TOKEN)
    fun retrofitIncludeAuthToken():Retrofit


}