package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.base.BaseMVP
import com.rathana.photo_book.entity.Photo

interface HomeMVP {

    interface View<Presenter> : BaseMVP.BaseView{
        fun onPhotosResponse(data: MutableList<Photo>)
    }

    interface Presenter : BaseMVP.basePresenter{
        fun setView(view: View<Presenter>)
        fun getPhotos(page: Int, limit: Int)
    }


    interface Interactor : BaseMVP.baseInteractor{
        fun getPhotos(queryMap: Map<String,Int>, response: HomeInteractorResponse)

    }

    interface HomeInteractorResponse : BaseMVP.BaseResponse{
        fun onPhotosResponse(data: MutableList<Photo>)
    }
}