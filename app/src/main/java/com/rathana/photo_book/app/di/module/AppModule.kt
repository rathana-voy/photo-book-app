package com.rathana.photo_book.app.di.module

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.rathana.photo_book.app.di.scope.ApplicationScope
import com.rathana.photo_book.entity.Photo
import dagger.Module
import dagger.Provides

@Module
class AppModule(var context: Context) {

    private var index=1

    @Provides
    @ApplicationScope
    fun provideContext():Context=this.context

    @Provides
    @ApplicationScope
    fun providePhoto() : Photo{
        index++
        return Photo(author = "author $index")
    }
}
