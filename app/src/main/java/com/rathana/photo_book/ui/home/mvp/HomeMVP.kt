package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.base.BaseMVP
import com.rathana.photo_book.entity.Photo

interface HomeMVP {

    interface View<T> : BaseMVP.BaseView{
        fun onPhotosResponse(data: MutableList<Photo>, canLoadMore: Boolean)
        fun onAddBookmarkSuccess(row: Int)
        fun onFindPhotoSuccess(photo: Photo)
        fun onPhotoEmpty()
        fun onLoadMorePhotos(data: MutableList<Photo>, canLoadMore: Boolean)
    }

    interface Presenter : BaseMVP.BasePresenter{
        fun setView(view: View<Presenter>)
        fun getPhotos(page: Int, limit: Int)
        fun addBookmark(photo: Photo)
        fun findPhoto(id: Int)
    }

    interface Interactor : BaseMVP.BaseInteractor{
        fun getPhotos(queryMap: Map<String,Int>, response: HomeInteractorResponse<MutableList<Photo>>)
        fun addBookmark(photo: Photo,response: HomeInteractorResponse<Int>)
        fun findPhoto(id :Int , response: HomeInteractorResponse<Photo>)
    }

    interface HomeInteractorResponse<T> : BaseMVP.BaseResponse{
        fun onPhotosResponse(t: T ?)
    }


}