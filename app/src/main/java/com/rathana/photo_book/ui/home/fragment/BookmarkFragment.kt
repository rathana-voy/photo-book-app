package com.rathana.photo_book.ui.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.FragmentComponent
import com.rathana.photo_book.base.BaseActivity
import com.rathana.photo_book.base.BaseFragment
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.ui.home.adapter.PhotoBookmarkAdapter
import com.rathana.photo_book.ui.home.mvp.BookmarkMVP
import com.rathana.photo_book.ui.home.mvp.BookmarkPresenter
import com.rathana.photo_book.utils.StaggerdGridItemDecoration
import com.rathana.photo_book.utils.dialog.LoadingProgress
import com.rathana.photo_book.utils.eventbus.AddBookmarkEvent
import com.rathana.photo_book.utils.eventbus.ItemClickedEvent
import com.rathana.photo_book.utils.widget.HttpLoadImageHelper
import com.rathana.photo_book.utils.widget.LoadMoreGridLayoutManager
import kotlinx.android.synthetic.main.fragment_bookmark.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class BookmarkFragment : BaseFragment(), BookmarkMVP.View {

    private lateinit var photoBookAdapter: PhotoBookmarkAdapter
    private val photoBookmarks = mutableListOf<Photo>()
    @Inject
    lateinit var bookmarkPresenter: BookmarkPresenter
    @Inject
    internal lateinit var loading: LoadingProgress
    lateinit var layoutManager: LoadMoreGridLayoutManager
    private var isRemoveClicked = false
    private var currentPage = 1

    companion object {
        fun getInstance(): BookmarkFragment = BookmarkFragment()
    }

    override fun onInject(component: FragmentComponent) {
        component.inject(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bookmarkPresenter.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(BaseActivity.bottomNavigationTitles[1])
        bookmarkPresenter.setView(this)
        //loading.hide()
        bookmarkPresenter.getPhotos(currentPage, 30)

    }

    private fun setupRecyclerView() {
        photoBookAdapter = PhotoBookmarkAdapter(activity!!, photoBookmarks)
        photoBookAdapter.canLoadMore = false
        rvPhotoBookmark?.setHasFixedSize(true)
        layoutManager = LoadMoreGridLayoutManager(activity!!, 2)
        layoutManager.setOnLoadMoreListener(object : LoadMoreGridLayoutManager.OnLoadMoreCallback {
            override fun onLoadMoreFromBottom() {
                currentPage++
                bookmarkPresenter.loadMorePhotos(currentPage, 30)
            }
        })
        rvPhotoBookmark?.layoutManager = layoutManager
        rvPhotoBookmark?.addItemDecoration(StaggerdGridItemDecoration(20))
        rvPhotoBookmark?.adapter = photoBookAdapter
    }

    /**
     * ==========
     * Event bus
     * ==========
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDeletePhotoClicked(photoEventBus: ItemClickedEvent) {
        bookmarkPresenter.remove(photoEventBus.photo!!)
        photoBookmarks.remove(photoEventBus.photo!!)
        photoBookAdapter.notifyItemRemoved(photoEventBus.position!!)
        isRemoveClicked = true
        Log.e("pos", "${photoEventBus.position}")

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddBookmarkReceived(bookmarkEvent: AddBookmarkEvent) {
        photoBookAdapter.addPhotoBookmark(bookmarkEvent.photo)
        Log.e("onAddBookmarkReceived", "${bookmarkEvent.photo}")
    }

    /**
     * ==========
     * block data response
     * ==========
     */
    override fun onPhotosSuccess(data: MutableList<Photo>, canLoadMore: Boolean) {
        photoBookmarks.addAll(data)
        setupRecyclerView()
        photoBookAdapter.canLoadMore = canLoadMore
        layoutManager.loadingFinished()
        //bookmarkPresenter.onDestroy()
        Log.e("bookmark data", "$data")
    }

    override fun onLoadMorePhotosSuccess(data: MutableList<Photo>, canLoadMore: Boolean) {
        photoBookAdapter.addLoadMoreItems(data)
        photoBookAdapter.canLoadMore = canLoadMore
        layoutManager.loadingFinished()
        //bookmarkPresenter.onDestroy()
        Log.e("bookmark data", "$data")
    }

    override fun onRemovePhotoSuccess() {
        Toast.makeText(activity!!, "removed", Toast.LENGTH_SHORT).show()
        bookmarkPresenter.onDestroy()
    }

    override fun onError(smg: String) {
        Log.e("bookmark fragment", smg)
        loading.hide()
        bookmarkPresenter.onDestroy()
        layoutManager.loadingFinished()
    }

    override fun onHideLoading() {
        loading.hide()
        bookmarkPresenter.onDestroy()
        layoutManager.loadingFinished()
    }

    override fun onShowLoading() {
        //loading.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        isFragmentRecreateView = true
        loading.hide()
    }
}