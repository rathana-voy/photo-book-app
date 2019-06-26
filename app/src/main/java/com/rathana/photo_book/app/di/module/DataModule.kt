package com.rathana.photo_book.app.di.module

import com.rathana.photo_book.app.data.datamanager.PhotoDataManger
import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.app.di.scope.ActivityScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class DataModule {


    @Provides
    @ActivityScope
    fun providePhotoService(@Named(NetworkModule.NO_AUTH_TOKEN) retrofit: Retrofit): PhotoService =
        retrofit.create(PhotoService::class.java)


    /**
     *  generate service
     */

    @Provides
    @ActivityScope
    fun providePhotoDataManager(photoService: PhotoService): PhotoDataManger=
        PhotoDataManger(photoService)
}