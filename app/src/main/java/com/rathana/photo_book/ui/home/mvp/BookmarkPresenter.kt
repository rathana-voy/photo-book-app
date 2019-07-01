package com.rathana.photo_book.ui.home.mvp

import com.rathana.photo_book.entity.Photo
import javax.inject.Inject

class BookmarkPresenter : BookmarkMVP.Presenter {

    lateinit var mView: BookmarkMVP.View
    protected var bookmarkInteractor: BookmarkInteractor

    @Inject
    constructor(bookmarkInteractor: BookmarkInteractor) {
        this.bookmarkInteractor = bookmarkInteractor
    }

    override fun setView(view: BookmarkMVP.View) {
        mView = view
    }

    override fun onDestroy() {
        bookmarkInteractor.onDestroy()
    }

    override fun remove(photo: Photo) {
        this.bookmarkInteractor.removePhoto(photo, response = object : BookmarkMVP.BookmarkInteractorResponse<Boolean> {
            override fun onComplete() {
                mView.onRemovePhotoSuccess()
            }

            override fun onSuccessResponse(t: Boolean) {

            }

            override fun onError(smg: String) {
                mView.onError(smg)
            }
        })
    }

    override fun getPhotos(page: Int, limit: Int) {
        this.mView.onShowLoading()
        this.bookmarkInteractor.getPhotos(
            page,
            limit,
            response = object : BookmarkMVP.BookmarkInteractorResponse<MutableList<Photo>> {
                override fun onComplete() {
                    mView.onHideLoading()
                }

                override fun onSuccessResponse(t: MutableList<Photo>) {
                    mView.onPhotosSuccess(t, (limit == t.size))
                    mView.onHideLoading()
                }

                override fun onError(smg: String) {
                    mView.onError(smg)
                    mView.onHideLoading()
                }
            })
    }

    override fun loadMorePhotos(page: Int, limit: Int) {
        this.bookmarkInteractor.getPhotos(
            page,
            limit,
            response = object : BookmarkMVP.BookmarkInteractorResponse<MutableList<Photo>> {
                override fun onComplete() {
                    mView.onHideLoading()
                }

                override fun onSuccessResponse(t: MutableList<Photo>) {
                    mView.onLoadMorePhotosSuccess(t, (limit == t.size))
                    mView.onHideLoading()
                }

                override fun onError(smg: String) {
                    mView.onError(smg)
                    mView.onHideLoading()
                }
            })
    }


}