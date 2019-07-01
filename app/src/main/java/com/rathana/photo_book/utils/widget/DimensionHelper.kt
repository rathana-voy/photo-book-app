package com.rathana.photo_book.utils.widget

import android.content.Context
import android.graphics.Point
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.photo_item_layout.view.*

class DimensionHelper {


    companion object{
        fun fitTwoItem(activity: AppCompatActivity,targetView : View,originalWidth:Int,originalHeight : Int,extraHeight: Int?=0){
            val display = activity.getWindowManager().getDefaultDisplay()
            val size = Point()
            display.getSize(size)
            val width=(size.x/2)
            val boxWidthHeight = HttpLoadImageHelper.scaleDownSpecify(
                newWidth = width,
                originalWidth = originalWidth,
                originalHeight = originalHeight
            )
            targetView.layoutParams.width=boxWidthHeight[0]
            targetView.layoutParams.height=boxWidthHeight[1]+extraHeight!!
        }
    }

}