package com.rathana.photo_book.app.di.compoment

import android.content.Context
import com.rathana.photo_book.app.data.datamanager.PhotoDataManger
import com.rathana.photo_book.app.data.local.dao.PhotoBookDatabase
import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.app.di.module.FragmentModule
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.app.di.scope.FragmentScope
import com.rathana.photo_book.ui.home.fragment.BookmarkFragment
import com.rathana.photo_book.ui.home.fragment.HomeFragment
import com.rathana.photo_book.ui.home.fragment.SettingFragment
import com.rathana.photo_book.ui.home.mvp.BookmarkMVP
import com.rathana.photo_book.ui.home.mvp.HomePresenter
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Named

@Component(dependencies = [ActivityComponent::class],modules = [FragmentModule::class])
@FragmentScope
interface FragmentComponent {
    fun context(): Context

    //provide dependency from App Module
    @Named(NetworkModule.NO_AUTH_TOKEN)
    fun retrofit(): Retrofit
    @Named(NetworkModule.AUTH_TOKEN)
    fun retrofitIncludeAuthToken(): Retrofit

    //provide service from Data Module
    fun photoService(): PhotoService
    fun photoDataManager(): PhotoDataManger
    fun photoBookDatabase(): PhotoBookDatabase

    fun homePresenter():HomePresenter

    fun inject(fragment: HomeFragment)
    fun inject(fragment: BookmarkFragment)
    fun inject(fragment: SettingFragment)


}