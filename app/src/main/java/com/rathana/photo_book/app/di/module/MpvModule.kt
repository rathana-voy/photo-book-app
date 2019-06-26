package com.rathana.photo_book.app.di.module

import com.rathana.photo_book.app.di.scope.ActivityScope
import com.rathana.photo_book.ui.home.mvp.HomeInteractor
import com.rathana.photo_book.ui.home.mvp.HomePresenter
import dagger.Module
import dagger.Provides

@Module
class MpvModule {


    @Provides
    @ActivityScope
    fun provideHomePresenter(interactor: HomeInteractor): HomePresenter= HomePresenter(interactor)
}