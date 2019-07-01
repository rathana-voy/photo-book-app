package com.rathana.photo_book.app.data.datamanager

import android.util.Log
import com.rathana.photo_book.app.data.local.dao.PhotoBookDatabase
import com.rathana.photo_book.entity.Paging
import com.rathana.photo_book.entity.Photo
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class BookmarkDataManager : AbsDataManager<MutableList<Photo>>{

    protected var photoBookDatabase : PhotoBookDatabase
    @Inject
    constructor(photoBookDatabase: PhotoBookDatabase){
        this.photoBookDatabase=photoBookDatabase
    }

    fun getPhoto(page: Int, limit: Int, response: ObservableCallback<MutableList<Photo>>){
        val paging= Paging(page,limit)
        compositeDisposable.add(
            observableRequest(photoBookDatabase.photoDao().getPhotoBookmarks(limit=paging.limit,offset = paging.offset))
            .subscribeWith(object : DisposableObserver<MutableList<Photo>>(){
                override fun onComplete() {
                    response.onComplete()
                }

                override fun onError(e: Throwable) {
                    response.onError(e.toString())
                }

                override fun onNext(t: MutableList<Photo>) {
                    response.onNext(t)
                }
            })
        )
    }

    fun removePhoto(photo : Photo,response: SingleCallback<Boolean>){
        compositeDisposable.add(
            completableRequest(photoBookDatabase.photoDao().remove(photo))
                .subscribe (
                    {
                      response.onComplete(true)
                    },
                    {
                        response.onError(it.toString())
                    }
                )
        )
    }
}