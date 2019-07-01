package com.rathana.photo_book.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Photo (
    @PrimaryKey(autoGenerate = false)
    val id:Int?=0,
    val author: String?="",
    val width :Int?= 0,
    val height:Int ?=0,
    val url : String?="",
    @SerializedName("download_url")
    @ColumnInfo(name = "download_url")
    val downloadUrl:String?="",
    var isBookmark: Boolean?=false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(author)
        parcel.writeValue(width)
        parcel.writeValue(height)
        parcel.writeString(url)
        parcel.writeString(downloadUrl)
        parcel.writeValue(isBookmark)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }

}