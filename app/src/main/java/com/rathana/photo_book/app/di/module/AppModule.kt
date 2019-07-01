package com.rathana.photo_book.app.di.module

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.rathana.photo_book.app.data.local.dao.PhotoBookDatabase
import com.rathana.photo_book.app.di.scope.ApplicationScope
import com.rathana.photo_book.entity.Photo
import dagger.Module
import dagger.Provides

@Module
class AppModule(var application: Application) {

    private var index=1

    @Provides
    @ApplicationScope
    fun provideContext():Application= application

    @Provides
    @ApplicationScope
    fun providePhoto() : Photo{
        index++
        return Photo(author = "author $index")
    }

    @Provides
    @ApplicationScope
    fun providePhotoBookDatabase(context: Application): PhotoBookDatabase= PhotoBookDatabase.getInstance(context)
}
