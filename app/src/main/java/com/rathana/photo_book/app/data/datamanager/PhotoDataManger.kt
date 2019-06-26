package com.rathana.photo_book.app.data.datamanager

import com.rathana.photo_book.app.data.service.PhotoService
import com.rathana.photo_book.entity.Photo
import io.reactivex.observers.DisposableObserver
import retrofit2.http.QueryMap
import javax.inject.Inject

class PhotoDataManger : AbsDataManager<MutableList<Photo>>{


    var photoService: PhotoService
    @Inject
    constructor(photoService: PhotoService){
        this.photoService=photoService
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

}