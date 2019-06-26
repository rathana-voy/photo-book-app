package com.rathana.photo_book.app.data.service

import com.rathana.photo_book.entity.Photo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface PhotoService {

    @GET("v2/list")
    fun getPhotos(@QueryMap map: Map<String,Int>): Observable<MutableList<Photo>>

    @GET("v2/list")
    fun getPhotos(): Observable<MutableList<Photo>>

}