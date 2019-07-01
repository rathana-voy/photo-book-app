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
        var canLoadMore=false
        val map = mutableMapOf(Pair("page",page), Pair("limit",limit))
        homeInteractor.getPhotos(queryMap = map,response = object : HomeMVP.HomeInteractorResponse<MutableList<Photo>>{
            override fun onPhotosResponse(data: MutableList<Photo>?) {

                view.onPhotosResponse(data!! ,canLoadMore = (limit== data!!.size) )
                view.onHideLoading()
            }

            override fun onError(smg: String) {
                view.onError(smg)
                view.onHideLoading()
            }
        })
    }


    fun loadMorePhotos(page: Int, limit: Int) {
        val map = mutableMapOf(Pair("page",page), Pair("limit",limit))
        homeInteractor.getPhotos(queryMap = map,response = object : HomeMVP.HomeInteractorResponse<MutableList<Photo>>{
            override fun onPhotosResponse(data: MutableList<Photo>?) {
                view.onLoadMorePhotos(data!! ,canLoadMore = (limit== data!!.size) )
                view.onHideLoading()
            }

            override fun onError(smg: String) {
                view.onError(smg)
                view.onHideLoading()
            }
        })
    }
    override fun addBookmark(photo: Photo){
        this.view.onShowLoading()
        homeInteractor.addBookmark(photo,response = object : HomeMVP.HomeInteractorResponse<Int>{

            override fun onError(smg: String) {
                view.onError(smg)
                view.onHideLoading()
            }

            override fun onPhotosResponse(t: Int?) {
                view.onAddBookmarkSuccess(t!!)
                view.onHideLoading()
            }
        })
    }

    override fun findPhoto(id: Int) {
        homeInteractor.findPhoto(id,response = object : HomeMVP.HomeInteractorResponse<Photo>{
            override fun onPhotosResponse(t: Photo?) {
                view.onFindPhotoSuccess(t!!)
            }

            override fun onError(smg: String) {
                view.onError(smg)
                view.onPhotoEmpty()
            }
        })
    }
}