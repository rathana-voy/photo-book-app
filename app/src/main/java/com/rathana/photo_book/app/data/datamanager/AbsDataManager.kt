package com.rathana.photo_book.app.data.datamanager

import com.rathana.photo_book.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class AbsDataManager<T> {

    protected var compositeDisposable = CompositeDisposable()

    fun observableRequest(observable: Observable<T>): Observable<T>{
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    fun singleRequest(observable: Single<T>): Single<T>{
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }


    fun completableRequest(observable: Completable): Completable{
        return observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onDestroy(){
        compositeDisposable.clear()
    }
    interface ObservableCallback<T>{
        fun onNext(t:T)
        fun onComplete()
        fun onError(smg: String)
    }

    interface SingleCallback<T>{
        fun onComplete(t : T)
        fun onError(smg: String)
    }
}