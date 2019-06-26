package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.entity.Photo
import javax.inject.Inject

class HomePresenter : HomeMVP.Presenter {

    private var homeInteractor :HomeMVP.Interactor
    private lateinit var  view: HomeMVP.View<HomeMVP.Presenter>

    @Inject
    constructor(homeInteractor :HomeMVP.Interactor ){
        this.homeInteractor=homeInteractor
    }

    override fun setView(view: HomeMVP.View<HomeMVP.Presenter>) {
        this.view=view
    }

    override fun onDestroy() {
        homeInteractor.onDestroy()
    }

    override fun getPhotos(page: Int, limit: Int) {
        this.view.onShowLoading()
        val map = mutableMapOf(Pair("page",page), Pair("limit",limit))
        homeInteractor.getPhotos(queryMap = map,response = object : HomeMVP.HomeInteractorResponse{
            override fun onPhotosResponse(data: MutableList<Photo>) {
                view.onPhotosResponse(data)
                view.onHideLoading()
            }

            override fun onError(smg: String) {
                view.onError(smg)
                view.onHideLoading()
            }
        })
    }



}