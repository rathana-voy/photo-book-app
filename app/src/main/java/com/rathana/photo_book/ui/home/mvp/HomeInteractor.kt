package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.app.data.datamanager.AbsDataManager
import com.rathana.photo_book.app.data.datamanager.PhotoDataManger
import com.rathana.photo_book.base.BaseMVP
import com.rathana.photo_book.entity.Photo
import javax.inject.Inject

class HomeInteractor : HomeMVP.Interactor {

    var photoDataManger: PhotoDataManger
    @Inject
    constructor(photoDataManger: PhotoDataManger){
        this.photoDataManger=photoDataManger
    }

    override fun getPhotos(queryMap: Map<String, Int>,
                           response: HomeMVP.HomeInteractorResponse<MutableList<Photo>>) {

        val data = mutableListOf<Photo>()

        this.photoDataManger.getPhotos(queryMap,
            response = object :AbsDataManager.ObservableCallback<MutableList<Photo>>{
            override fun onNext(t: MutableList<Photo>) {
                data.addAll(t)
            }

            override fun onComplete() {
                response.onPhotosResponse(data)
            }

            override fun onError(smg: String) {
                response.onError(smg)
            }
        })

    }

    override fun addBookmark(photo: Photo, response: HomeMVP.HomeInteractorResponse<Int>) {
        photoDataManger.addPhotoBookmark(photo,response = object : AbsDataManager.SingleCallback<Int>{
            override fun onComplete(t: Int) {
                response.onPhotosResponse(t)
            }

            override fun onError(smg: String) {
                response.onError(smg)
            }
        })
    }

    override fun onDestroy(){
        this.photoDataManger.onDestroy()
    }

    override fun findPhoto(id: Int, response: HomeMVP.HomeInteractorResponse<Photo>) {
        this.photoDataManger.findPhoto(id,response = object : AbsDataManager.SingleCallback<Photo>{
            override fun onComplete(t: Photo) {
                response.onPhotosResponse(t)
            }

            override fun onError(smg: String) {
                response.onError(smg)
            }
        })
    }


}