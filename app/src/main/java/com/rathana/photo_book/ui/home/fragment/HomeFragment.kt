package com.rathana.photo_book.ui.home.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.paginate.Paginate
import com.rathana.photo_book.R
import com.rathana.photo_book.app.di.compoment.FragmentComponent
import com.rathana.photo_book.base.BaseActivity
import com.rathana.photo_book.base.BaseFragment
import com.rathana.photo_book.entity.Photo
import com.rathana.photo_book.ui.detail.DetailActivity
import com.rathana.photo_book.ui.home.adapter.PhotoAdapter
import com.rathana.photo_book.ui.home.mvp.HomeMVP
import com.rathana.photo_book.ui.home.mvp.HomePresenter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject
import com.rathana.photo_book.utils.StaggerdGridItemDecoration
import com.rathana.photo_book.utils.dialog.LoadingProgress
import com.rathana.photo_book.utils.eventbus.AddBookmarkEvent
import com.rathana.photo_book.utils.widget.HttpLoadImageHelper
import com.rathana.photo_book.utils.widget.LoadMoreGridLayoutManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeFragment : BaseFragment(), HomeMVP.View<HomeMVP.Presenter> {

    private lateinit var photoAdapter: PhotoAdapter
    private val photos = mutableListOf<Photo>()
    @Inject lateinit var homePresenter: HomePresenter
    private lateinit var photoBookmark: Photo
    @Inject internal lateinit var loading: LoadingProgress

    private lateinit var layoutManager: LoadMoreGridLayoutManager
    private var currentPage = 1
    override fun onInject(component: FragmentComponent) {
        component.inject(this)
    }

    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)

    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        return view;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(BaseActivity.bottomNavigationTitles[0])
        homePresenter.setView(this)
        loading.hide()
        homePresenter.getPhotos(page = currentPage, limit = 30)

    }

    private fun setupRecyclerView() {
        photoAdapter = PhotoAdapter(activity!!, photos)
        photoAdapter.canLoadMore = false
        rvPhoto?.setHasFixedSize(true)
        rvPhoto?.addItemDecoration(StaggerdGridItemDecoration(20))
        rvPhoto?.adapter = photoAdapter
        layoutManager = LoadMoreGridLayoutManager(activity!!, 2)
        rvPhoto.layoutManager = layoutManager
        layoutManager.setOnLoadMoreListener(object : LoadMoreGridLayoutManager.OnLoadMoreCallback {
            override fun onLoadMoreFromBottom() {
                currentPage++
                homePresenter.loadMorePhotos(page = currentPage, limit = 30)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onAddBookmark(photo: Photo) {
        photoBookmark = photo
        homePresenter.findPhoto(photo.id!!)
        Log.e("photo: ", "$photo")
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     *
     * data  response
     */
    override fun onPhotosResponse(data: MutableList<Photo>, canLoadMore: Boolean) {
        photos.addAll(data)
        setupRecyclerView()
        photoAdapter.canLoadMore = canLoadMore
        layoutManager.loadingFinished()
        Log.e("data", "$data")
    }

    override fun onLoadMorePhotos(data: MutableList<Photo>, canLoadMore: Boolean) {
        photoAdapter.addLoadMoreItems(data)
        photoAdapter.canLoadMore = canLoadMore
        layoutManager.loadingFinished()
    }

    override fun onFindPhotoSuccess(photo: Photo) {
        Toast.makeText(activity!!, "ready added to bookmark", Toast.LENGTH_SHORT).show()
    }

    override fun onAddBookmarkSuccess(row: Int) {
        EventBus.getDefault().post(AddBookmarkEvent(photoBookmark))
        Toast.makeText(activity!!, "add to bookmark", Toast.LENGTH_SHORT).show()
    }

    override fun onPhotoEmpty() {
        photoBookmark.isBookmark = true
        homePresenter.addBookmark(photoBookmark)
    }

    override fun onError(smg: String) {
        Log.e("data", "$smg")

        loading.hide()
    }

    override fun onHideLoading() {
        loading.hide()
    }

    override fun onShowLoading() {
        loading.show()
    }


    override fun onDestroy() {
        super.onDestroy()
        homePresenter.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loading.hide()
        isFragmentRecreateView = true
    }

}