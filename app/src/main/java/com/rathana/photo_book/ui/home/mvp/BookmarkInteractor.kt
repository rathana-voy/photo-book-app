package com.rathana.photo_book.ui.home.mvp

import android.util.Log
import com.rathana.photo_book.app.data.datamanager.AbsDataManager
import com.rathana.photo_book.app.data.datamanager.BookmarkDataManager
import com.rathana.photo_book.entity.Paging
import com.rathana.photo_book.entity.Photo
import javax.inject.Inject

class BookmarkInteractor : BookmarkMVP.Interactor {

    protected var bookmarkDataManager: BookmarkDataManager
    @Inject constructor(bookmarkDataManager: BookmarkDataManager){
        this.bookmarkDataManager=bookmarkDataManager
    }

    override fun onDestroy() {
        bookmarkDataManager.onDestroy()
    }

    override fun getPhotos(page: Int, limit: Int, response : BookmarkMVP.BookmarkInteractorResponse<MutableList<Photo>>) {
        val data = mutableListOf<Photo>()
        this.bookmarkDataManager.getPhoto(page, limit,response = object :AbsDataManager.ObservableCallback<MutableList<Photo>>{
            override fun onComplete() {
                response.onComplete()
                response.onSuccessResponse(data)
            }

            override fun onError(smg: String) {
                response.onError(smg)
            }

            override fun onNext(t:MutableList<Photo>) {
                data.addAll(t)
                response.onSuccessResponse(t)
            }
        })
    }

    override fun removePhoto(photo: Photo, response: BookmarkMVP.BookmarkInteractorResponse<Boolean>) {
        this.bookmarkDataManager.removePhoto(photo,response = object : AbsDataManager.SingleCallback<Boolean>{
            override fun onComplete(t:Boolean) {
                response.onComplete()
            }

            override fun onError(smg: String) {
                response.onError(smg)
            }
        })
    }
}