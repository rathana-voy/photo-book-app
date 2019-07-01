package com.rathana.photo_book.base

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rathana.photo_book.R
import com.rathana.photo_book.app.App
import com.rathana.photo_book.app.di.compoment.ActivityComponent
import com.rathana.photo_book.app.di.compoment.AppComponent
import com.rathana.photo_book.app.di.compoment.DaggerActivityComponent
import com.rathana.photo_book.app.di.module.ActivityModule
import com.rathana.photo_book.ui.home.fragment.BookmarkFragment
import com.rathana.photo_book.ui.home.fragment.HomeFragment

abstract class BaseActivity : AppCompatActivity() {

    lateinit var activityComponent: ActivityComponent
    val fragments = mutableListOf<Fragment>()
    lateinit var currentFragment: Fragment

    companion object {
        const val HOME_FRAGMENT = "home-fragment"
        const val BOOKMARK_FRAGMENT = "bookmark-fragment"
        const val SETTING_FRAGMENT = "setting-fragment"
        val bottomNavigationTitles = arrayOf("Gallery", "Bookmark", "Setting")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
            .activityModule(ActivityModule(this))
            .appComponent((application as App).appComponent).build()
        this.onInject(activityComponent)
        setContentView(setLayout())
    }

    @LayoutRes
    abstract fun setLayout(): Int;

    abstract fun onInject(activityComponent: ActivityComponent)

    protected fun replaceFragment(
        savedInstanceState: Bundle?, @IdRes container: Int,
        fragment: Fragment,
        tag: String? = ""
    ) {
        val transaction = supportFragmentManager.beginTransaction()
        var frm: Fragment?
        if (tag == HOME_FRAGMENT)
            frm = supportFragmentManager.findFragmentByTag(HOME_FRAGMENT)
        else if (tag == BOOKMARK_FRAGMENT)
            frm = supportFragmentManager.findFragmentByTag(BOOKMARK_FRAGMENT)
        else
            frm = supportFragmentManager.findFragmentByTag(SETTING_FRAGMENT)

        if (frm == null)
            frm = fragment
        if (savedInstanceState == null) {
            transaction.replace(container, frm, tag ?: "")
            transaction.commit()
        }
    }

    protected fun addFragments(fragments: MutableList<Fragment>, @IdRes container: Int, currentIndex: Int? = 0) {
        this.fragments.addAll(fragments)
        val transaction = supportFragmentManager.beginTransaction()
        for (frm in fragments) {
            transaction.add(container, frm)
            if (frm is HomeFragment) transaction.show(frm) else transaction.hide(frm)
        }
        currentFragment = fragments[currentIndex!!]
        transaction.commit()
    }

    protected fun switchFragment(tag: String, fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        var frm = supportFragmentManager.findFragmentByTag(tag)

        if (frm == null)
            frm = fragment

        transaction.hide(currentFragment)
        transaction.show(frm)
        transaction.commit()
        currentFragment = frm

    }

    fun showArrowBack(toolbar: Toolbar) {
        toolbar.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back_primary_color_24dp))
        toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}