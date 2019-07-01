package com.rathana.photo_book.ui.home.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.module.NetworkModule
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.ui.detail.DetailActivity
import com.rathana.photo_book.utils.ImageBinderHelper
import com.rathana.photo_book.utils.eventbus.ItemClickedEvent
import com.rathana.photo_book.utils.widget.DimensionHelper
import com.rathana.photo_book.utils.widget.HttpLoadImageHelper
import com.rathana.photo_book.utils.widget.LoadingViewHolder
import kotlinx.android.synthetic.main.photo_bookmark_item_layout.view.*
import kotlinx.android.synthetic.main.photo_bookmark_item_layout.view.imageContainer
import kotlinx.android.synthetic.main.photo_item_layout.view.*
import kotlinx.android.synthetic.main.photo_item_layout.view.author
import kotlinx.android.synthetic.main.photo_item_layout.view.photo
import org.greenrobot.eventbus.EventBus

class PhotoBookmarkAdapter(
    var context: Context,
    val photos: MutableList<Photo>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var canLoadMore = false
    lateinit var recyclerView: RecyclerView

    companion object {
        const val ITEM_PROGRESS = -1
        const val ITEM_LAYOUT = -2
    }

    override fun getItemCount(): Int =
        if (canLoadMore) {
            photos.size + 1
        } else {
            photos.size
        }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int =
        if (position < photos.size) ITEM_LAYOUT else ITEM_PROGRESS

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.binding(photos[position])
            holder.itemView.btnRemove.setOnClickListener {
                EventBus.getDefault()
                    .post(ItemClickedEvent(photo = photos[holder.adapterPosition], position = holder.adapterPosition))
            }

            holder.itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("data", photos[holder.adapterPosition])
                context.startActivity(intent)
            }
        } else {
            val layoutParams = holder.itemView.getLayoutParams() as StaggeredGridLayoutManager.LayoutParams
            layoutParams.isFullSpan = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when {
            viewType == ITEM_LAYOUT -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.photo_bookmark_item_layout,
                    parent,
                    false
                )
                return ViewHolder(view)
            }
            viewType == ITEM_PROGRESS -> return LoadingViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loading_more_layout,
                    parent,
                    false
                )
            )

        }

        return super.createViewHolder(parent, viewType)
    }

    fun addLoadMoreItems(items: MutableList<Photo>) {
        val previousItemCount = itemCount
        photos.addAll(items)
        notifyItemRangeInserted(previousItemCount + 1, items.size)
    }

    fun addPhotoBookmark(photo: Photo) {
        photos.add(0, photo)
        notifyItemInserted(0)
        recyclerView.smoothScrollToPosition(0)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun binding(photo: Photo) {

            //ImageBinderHelper.bind(context, itemView.photo, "${NetworkModule.HOST}/id/${photo.id}/250/350")
            itemView.author.text = photo.author
            val widthHeight = HttpLoadImageHelper.scaleDownSpecify(
                newWidth = 250,
                originalWidth = photo.width!!,
                originalHeight = photo.height!!
            )

            DimensionHelper.fitTwoItem(activity = context as AppCompatActivity,
                targetView = itemView.imageContainer,
                originalHeight = photo.height!!,
                originalWidth = photo.width!!,
                extraHeight = 200
            )
            val httpLoadImageHelper = HttpLoadImageHelper(context)
            httpLoadImageHelper.loadImage(
                "${NetworkModule.HOST}/id/${photo.id!!}/${widthHeight[0]}/${widthHeight[1]}",
                width = widthHeight[0],
                height = widthHeight[1],
                callBack = object : HttpLoadImageHelper.CallBack {
                    override fun onComplete() {
                        httpLoadImageHelper.onDestroy()
                    }

                    override fun onNext(bitmap: Bitmap?) {
                        itemView.photo.setImageBitmap(bitmap!!)
                    }

                    override fun onStart() {}
                    override fun onError(smg: String) {
                        Toast.makeText(context,smg, Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}