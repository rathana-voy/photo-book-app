package com.rathana.photo_book.base

interface BaseMVP{

    interface BaseView{
        fun onError(smg: String)
        fun onShowLoading()
        fun onHideLoading()
    }

    interface BaseResponse{
        fun onError(smg: String)
    }

    interface baseInteractor{
        fun onDestroy()
    }

    interface basePresenter{
        fun onDestroy()
    }
}