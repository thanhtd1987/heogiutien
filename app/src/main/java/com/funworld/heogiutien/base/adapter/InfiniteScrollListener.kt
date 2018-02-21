package com.funworld.heogiutien.base.delegateAdapater

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log

/**
 * Created by hieunt on 8/18/17.
 */
abstract class InfiniteScrollListener(private val loadMore : () -> Unit, private val positionChanged : (pos : Int) -> Unit) : RecyclerView.OnScrollListener(){

    private val TAG = "InfiniteScrollListener"
    private var scrolledDistance = 0
    private val HIDE_THRESHOLD = 20

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private val visibleThreshold = 2 // The minimum amount of items to have below your current scroll position before loading more.
    private var firstVisibleItem: Int = 0
    private var visibleItemCount:Int = 0
    private var totalItemCount:Int = 0

    private var infiniteScrollingEnabled = true

    private var controlsVisible = true

//    private var previousTotal = 0
//    private var loading = true
//    private var visibleThreshold = 2
//    private var firstVisibleItem = 0
//    private var visibleItemCount = 0
//    private var totalItemCount = 0

    // So TWO issues here.
    // 1. When the data is refreshed, we need to change previousTotal to 0.
    // 2. When we switch fragments and it loads itself from some place, for some
    // reason gridLayoutManager returns stale data and hence re-assigning it every time.

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val manager = recyclerView!!.layoutManager

        visibleItemCount = recyclerView.childCount
        if (manager is GridLayoutManager) {
//            val gridLayoutManager = manager
            firstVisibleItem = manager.findFirstVisibleItemPosition()
            totalItemCount = manager.itemCount
        } else if (manager is LinearLayoutManager) {
//            val linearLayoutManager = manager
            firstVisibleItem = manager.findFirstVisibleItemPosition()
            totalItemCount = manager.itemCount
        }

        positionChanged(firstVisibleItem)

        if (infiniteScrollingEnabled) {
            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }

            if (!loading && ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold))) {
                // End has been reached
                // do something
                loadMore()
                loading = true
            }
        }

        if (firstVisibleItem == 0) {
            if (!controlsVisible) {
                controlsVisible = true
            }

            return
        }

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            controlsVisible = false
            scrolledDistance = 0
        } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            controlsVisible = true
            scrolledDistance = 0
        }

        if (controlsVisible && dy > 0 || !controlsVisible && dy < 0) {
            scrolledDistance += dy
        }
    }

//    fun stopInfiniteScrolling() {
//        infiniteScrollingEnabled = false
//    }
//
//    fun onDataCleared() {
//        previousTotal = 0
//    }

}