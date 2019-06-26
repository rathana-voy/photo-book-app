package com.rathana.photo_book.entity

import com.google.gson.annotations.SerializedName

data class Photo(
    val id:Int?=0,
    val author: String?="",
    val width :Int?= 0,
    val height:Int ?=0,
    val url : String?="",
    @SerializedName("download_url")
    val downloadUrl:String?=""
)