package com.rathana.photo_book.utils.widget

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class LoadMoreGridLayoutManager(var context: Context, spanCount: Int) : StaggeredGridLayoutManager(spanCount,StaggeredGridLayoutManager.VERTICAL) {

    private var mLoadMoreBeforeEnd: Int = 0
    private var mIsLoadingMore = false
    private var mOnLoadMoreListener: OnLoadMoreCallback? = null
    private var pastVisibleItems:Int = 0
    fun getLoadMoreBeforeEnd(): Int {
        return mLoadMoreBeforeEnd
    }

    fun setLoadMoreBeforeEnd(loadMoreBeforeEnd: Int) {
        mLoadMoreBeforeEnd = loadMoreBeforeEnd
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreCallback) {
        mOnLoadMoreListener = onLoadMoreListener
    }

    fun loadingFinished() {
        mIsLoadingMore = false
    }

    override fun scrollVerticallyBy (dy: Int, recycler: RecyclerView.Recycler, state: RecyclerView.State) : Int {
        if (mOnLoadMoreListener != null) {

            val mVisibleItemCount = this.childCount
            val mTotalItemCount = this.itemCount
            pastVisibleItems=findFirstVisibleItemPositions(null)[0]
            if (!mIsLoadingMore) {
                if (mVisibleItemCount + pastVisibleItems >= mTotalItemCount - mLoadMoreBeforeEnd) {
                    mIsLoadingMore = true
                    mOnLoadMoreListener!!.onLoadMoreFromBottom()

                }
            }
        }

        return super.scrollVerticallyBy(dy, recycler, state)
    }

    interface OnLoadMoreCallback {
        fun onLoadMoreFromBottom()
    }

}