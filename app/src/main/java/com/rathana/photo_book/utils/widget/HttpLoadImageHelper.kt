package com.rathana.photo_book.utils.widget

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.rathana.photo_book.R
import com.rathana.photo_book.entity.Photo
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class HttpLoadImageHelper(var context: Context) {

    var imageWith = 0;
    var imageHeight = 0
    lateinit var loadImagTask: LoadImageTask
    protected var compositeDisposable = CompositeDisposable()

    companion object {
        fun scaleDownSpecify(
            originalWidth: Int,
            originalHeight: Int,
            newWidth: Int? = 0,
            newHeight: Int? = 0
        ): Array<Int> {
            var width: Int
            var height: Int

            when {
                newWidth != 0 && newHeight != 0 -> {
                    width = if (newWidth!! > originalWidth) originalWidth else newWidth
                    height = if (newHeight!! > originalHeight) originalHeight else newHeight
                    return arrayOf(width, height)
                }
                newWidth != 0 -> {
                    width = if (newWidth!! > originalWidth) originalWidth else newWidth
                    height = width * originalHeight / originalWidth
                    return arrayOf(width, height)
                }
                newHeight != 0 -> {
                    height = if (newHeight!! > originalHeight) originalHeight else newHeight
                    width = height * originalWidth / originalHeight
                    return arrayOf(width, height)
                }
            }

            return arrayOf()
        }

    }

    fun loadImage(url: String, originalWidth: Int, originalHeight: Int, newWidth: Int?, callBack: CallBack) {
        Log.e("url", url)
        this.imageWith = newWidth ?: 568
        imageHeight = imageWith * originalHeight / originalWidth
        loadImagTask = LoadImageTask(callBack)
        loadImagTask.execute(url)
        //request(url,callBack)
    }

    fun loadImage(url: String, width: Int, height: Int, callBack: CallBack) {
        Log.e("url", url)
        imageWith = width
        imageHeight = height
        loadImagTask = LoadImageTask(callBack)
        loadImagTask.execute(url)
        //request(url,callBack)
    }

    private fun request(url: String, callBack: CallBack) {
        val target = Glide.with(context)
            .asBitmap()
            .load(url)
            .placeholder(R.drawable.thubmnail)
            .submit(imageWith, imageHeight)
            .get()

        val observable = Observable.just(target)
        callBack.onStart()
        compositeDisposable.add(
            observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    callBack.onNext(it)
                }, {
                    Log.e("Load Image", "an error occur whiledownloading")
                }, {
                    callBack.onComplete()
                })
        )
    }

    inner class LoadImageTask(val callBack: CallBack) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg params: String?): Bitmap? {
            try {
                return Glide.with(context)
                    .asBitmap()
                    .load(params[0])
                    .placeholder(R.drawable.thubmnail)
                    .submit(imageWith, imageHeight)
                    .get()
            }catch (e : Exception ){
                callBack.onError("broken while loading image from server")
                return BitmapFactory.decodeResource(context.resources,R.drawable.cloud_fail)
            }
        }

        override fun onPreExecute() {
            callBack.onStart()
        }

        override fun onPostExecute(result: Bitmap?) {
            callBack.onNext(result)
            callBack.onComplete()
            //loadImagTask.cancel(false)
        }
    }

    interface CallBack {
        fun onStart()
        fun onNext(bitmap: Bitmap?)
        fun onComplete()
        fun onError(smg: String)
    }

    fun onDestroy() {
        loadImagTask.cancel(true)
        compositeDisposable.clear()
    }

}