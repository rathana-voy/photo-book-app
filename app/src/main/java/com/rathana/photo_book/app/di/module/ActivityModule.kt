package com.rathana.photo_book.app.di.module

import android.content.Context
import com.rathana.photo_book.app.di.scope.ActivityScope
import com.rathana.photo_book.app.di.scope.FragmentScope
import com.rathana.photo_book.utils.dialog.LoadingProgress
import dagger.Module
import dagger.Provides

@Module
class ActivityModule (var context: Context) {

    @Provides
    @ActivityScope
    fun provideContext(): Context = context

    @Provides
    @ActivityScope
    fun provideLoading(context: Context): LoadingProgress = LoadingProgress(context)

}