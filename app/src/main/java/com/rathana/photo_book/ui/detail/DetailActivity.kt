package com.rathana.photo_book.ui.detail

import android.graphics.Bitmap
import android.graphics.Point
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.ActivityComponent
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.base.BaseActivity
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.utils.ImageBinderHelper
import com.rathana.photo_book.utils.dialog.LoadingProgress
import com.rathana.photo_book.utils.widget.HttpLoadImageHelper
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.lang.Exception
import javax.inject.Inject

class DetailActivity : BaseActivity() {

    @Inject
    lateinit var laodding: LoadingProgress

    override fun onInject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setLayout(): Int = R.layout.activity_detail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showArrowBack(toolbar)
        if (intent != null) {
            imageContainer?.visibility = View.GONE
            val photo = intent!!.getParcelableExtra("data") as Photo
            toolbarTitle!!.text = photo.author!!
            val widthHeight = HttpLoadImageHelper.scaleDownSpecify(
                newWidth = 780,
                originalWidth = photo.width!!,
                originalHeight = photo.height!!
            )
            HttpLoadImageHelper(this).loadImage(
                "${NetworkModule.HOST}/id/${photo.id!!}/${widthHeight[0]}/${widthHeight[1]}",
                width = widthHeight[0],
                height = widthHeight[1],
                callBack = object : HttpLoadImageHelper.CallBack {
                    override fun onComplete() {
                        laodding.hide()

                    }

                    override fun onNext(bitmap: Bitmap?) {
                        imageContainer?.visibility = View.VISIBLE
                        imageTitle.text = photo.author
                        imageSize.text = "Original size: ${photo.width} X ${photo.height}"
                        try {
                            bindImageView(detailImage, bitmap!!, widthHeight[0], widthHeight[1])
                        }catch (e :Exception){
                            detailImage.setImageResource(R.drawable.cloud_fail)
                        }

                        laodding.hide()
                    }

                    override fun onStart() {
                        laodding.show()
                    }

                    override fun onError(smg: String) {
                        laodding.hide()
                    }
                })
        }

    }

    fun bindImageView(img: ImageView, bitmap: Bitmap, width: Int, height: Int) {
        val display = getWindowManager().getDefaultDisplay()
        val size = Point()
        display.getSize(size)
        detailImage.layoutParams.width = size.x
        detailImage.layoutParams.height = size.x * height / width
        detailImage.setImageBitmap(bitmap!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_option_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when{
            item!!.itemId==R.id.btnDownload->{
                return true
            }
            item!!.itemId==R.id.btnBookmark->{
                return true
            }
            else -> return true
        }
    }


}
