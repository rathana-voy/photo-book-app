package com.rathana.photo_book.app.di.module

import android.content.Context
import com.rathana.photo_book.app.di.scope.FragmentScope
import com.rathana.photo_book.ui.home.mvp.*
import com.rathana.photo_book.utils.dialog.LoadingProgress
import com.rathana.photo_book.utils.widget.HttpLoadImageHelper
import dagger.Module
import dagger.Provides

@Module
class FragmentModule() {

    @Provides
    @FragmentScope
    fun provideHomePresenter(interactor: HomeInteractor): HomePresenter = HomePresenter(interactor)

    @Provides
    @FragmentScope
    fun provideBookmarkPresenter(interactor: BookmarkInteractor): BookmarkPresenter = BookmarkPresenter(interactor)

    @Provides
    @FragmentScope
    fun provideLoading(context: Context): LoadingProgress = LoadingProgress(context)

    @Provides
    @FragmentScope
    fun provideHttpLoadImage(context: Context): HttpLoadImageHelper = HttpLoadImageHelper(context)
}