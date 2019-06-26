package com.rathana.photo_book.app.di.module

import com.rathana.photo_book.app.di.scope.ActivityScope
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class ActivityModule {

    @Provides
    @ActivityScope
    @Named("url")
    fun provideUrl():String="https://google.com.kh"
}