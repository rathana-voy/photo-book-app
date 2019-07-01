package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.base.BaseMVP
import com.rathana.photo_book.entity.Photo

interface BookmarkMVP {

    interface View : BaseMVP.BaseView{
        fun onPhotosSuccess(data: MutableList<Photo>,canLoadMore: Boolean)
        fun onLoadMorePhotosSuccess(data: MutableList<Photo>,canLoadMore: Boolean)
        fun onRemovePhotoSuccess()
    }

    interface Presenter: BaseMVP.BasePresenter{
        fun setView(view: View)
        fun getPhotos(page: Int, limit: Int)
        fun loadMorePhotos(page: Int, limit: Int)
        fun remove(photo: Photo)

    }

    interface Interactor: BaseMVP.BaseInteractor{
        fun getPhotos(page : Int, limit: Int , response: BookmarkInteractorResponse<MutableList<Photo>>)
        fun removePhoto(photo: Photo , response: BookmarkInteractorResponse<Boolean>)
    }

    interface BookmarkInteractorResponse <T>: BaseMVP.BaseResponse{
        fun onSuccessResponse(t : T)
        fun onComplete()
    }

}