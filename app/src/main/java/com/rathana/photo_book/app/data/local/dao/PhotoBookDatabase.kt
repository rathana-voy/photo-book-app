package com.rathana.photo_book.app.data.local.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rathana.photo_book.entity.Photo

@Database(entities = [Photo::class],version = PhotoBookDatabase.VERSION)
abstract class PhotoBookDatabase : RoomDatabase(){

    companion object{
        const val VERSION = 1
        const val DB_NAME="photo_book_db"

        fun getInstance(context: Context) : PhotoBookDatabase=
            Room.databaseBuilder(context,PhotoBookDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration().build()
    }

   abstract fun photoDao() : PhotoDao



}