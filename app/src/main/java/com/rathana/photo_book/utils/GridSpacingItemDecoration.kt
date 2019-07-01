package com.rathana.photo_book.utils

import androidx.recyclerview.widget.RecyclerView
import android.R.attr.right
import android.R.attr.left
import android.graphics.Rect
import android.view.View


class GridSpacingItemDecoration(var spanCount :Int,
                                var spacing: Int,
                                var includeEdge: Boolean)
    : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, position: Int, parent: RecyclerView) {

        val column = position % spanCount // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing // item bottom
        } else {
            outRect.left = column * spacing / spanCount // column * ((1f / spanCount) * spacing)
            outRect.right =
                spacing - (column + 1) * spacing / spanCount // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing // item top
            }
        }
    }

}