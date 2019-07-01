package com.rathana.photo_book.app.data.datamanager

import com.rathana.photo_book.app.data.local.dao.PhotoBookDatabase
import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.entity.Photo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PhotoDataManger : AbsDataManager<MutableList<Photo>>{


    var photoService: PhotoService
    var photoBookDatabase : PhotoBookDatabase
    @Inject
    constructor(photoService: PhotoService,photoBookDatabase: PhotoBookDatabase){
        this.photoService=photoService
        this.photoBookDatabase=photoBookDatabase
    }

    fun getPhotos(queryMap: Map<String,Int>,response: ObservableCallback<MutableList<Photo>>){
        compositeDisposable.add(observableRequest(this.photoService.getPhotos(queryMap))
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
            }))
    }

    fun addPhotoBookmark(photo: Photo, response: SingleCallback<Int>){
        compositeDisposable.add(
        photoBookDatabase.photoDao().addPhoto(photo = photo).
           observeOn(AndroidSchedulers.mainThread())
           .subscribeOn(Schedulers.io())
           .subscribe({
                response.onComplete(1)
           },{
                response.onError(it.toString())
           })
        )
    }

    fun findPhoto(id :Int, response: SingleCallback<Photo>){
        compositeDisposable.add(
            photoBookDatabase.photoDao().findPhoto(id).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    response.onComplete(it)
                },{
                    response.onError(it.toString())
                })
        )
    }

}