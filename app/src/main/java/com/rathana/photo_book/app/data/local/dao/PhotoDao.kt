package com.rathana.photo_book.app.data.local.dao

import androidx.room.*
import com.rathana.photo_book.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface PhotoDao {

    @Insert()
    fun addPhoto(photo: Photo) :Completable
    @Delete
    fun remove(photo: Photo):Completable

    @Update
    fun edit(photo: Photo) : Completable

    @Query("SELECT * FROM photo ORDER BY id DESC LIMIT :limit OFFSET :offset")
    fun getPhotoBookmarks(limit: Int,offset: Int): Observable<MutableList<Photo>>

    @Query("SELECT count(id) FROM photo")
    fun countPhotos(): Single<Int>

    @Query("SELECT * FROM photo WHERE id= :id")
    fun findPhoto(id:Int) : Single<Photo>
}
