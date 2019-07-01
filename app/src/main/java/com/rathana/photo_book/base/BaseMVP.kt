package com.rathana.photo_book.base

import com.rathana.photo_book.entity.Photo

interface BaseMVP{

    interface BaseView{
        fun onError(smg: String)
        fun onShowLoading()
        fun onHideLoading()
    }

    interface BaseResponse{
        fun onError(smg: String)
    }

    interface BaseInteractor{
        fun onDestroy()
    }

    interface BasePresenter{
        fun onDestroy()
    }
}