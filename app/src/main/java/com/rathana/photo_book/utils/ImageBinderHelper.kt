package com.rathana.photo_book.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rathana.photo_book.R

class ImageBinderHelper {

    companion object{
        fun bind(context: Context,imageView: ImageView, url : String?=null){

            Glide.with(context)
                .load(url?:R.drawable.loading)
                .placeholder(R.drawable.thubmnail)
                .centerCrop()
                .into(imageView)
        }

    }


}