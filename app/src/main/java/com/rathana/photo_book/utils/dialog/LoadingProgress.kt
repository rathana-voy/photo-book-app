package com.rathana.photo_book.utils.dialog

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rathana.photo_book.R
import kotlinx.android.synthetic.main.loading_layout.*

class LoadingProgress(val context: Context) {

    lateinit var dialog: Dialog
    init{dialog= Dialog(context) }
    fun show(){
        dialog= Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_layout)
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent)
        Glide.with(context)
            .asGif()
            .load(R.drawable.loading)
            .centerCrop()
            .into(dialog.loading!!)
        dialog.show()
    }

    fun hide(){
        if(dialog!=null){
            dialog.dismiss()
        }
    }

}