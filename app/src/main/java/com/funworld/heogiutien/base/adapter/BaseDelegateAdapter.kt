package com.funworld.heogiutien.base.delegateAdapater

//import android.arch.lifecycle.MutableLiveData
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup

/**
 * Created by ThanhTD on 8/23/2017.
 */

abstract class BaseDelegateAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    abstract fun setupViewType (views : List<ViewType>) : List<ViewType>

    var items: ArrayList<ViewType>
    var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    val loadingItem = object : ViewType {
        override fun getViewType() = AdapterConstants.LOADING
    }

    var itemPerPage = 10 // default

//    val scrollPositionObs by lazy { MutableLiveData<Int>() }
    private var lastPositionUpdated = -1
    private var currentVisiblePosition = 0

    protected var loadMoreCallback: LoadMoreCallback? = null

    interface LoadMoreCallback {
        fun addMoreData()
    }

    var maxPage = 1
    var currentPage = 1

    init {
        delegateAdapters.put(AdapterConstants.LOADING, LoadingDelegateAdapter())
        items = ArrayList()
        items.add(loadingItem)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(items[position].getViewType()).onBindViewHolder(holder, items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            delegateAdapters.get(viewType).onCreateViewHolder(parent)

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int) = items[position].getViewType()

    fun addData(views: List<ViewType>) {
        if (views.isNotEmpty()) {
            var initPosition = items.size - 1
            if(initPosition < 0) initPosition = 0
            items.addAll(initPosition, setupViewType(views))
            notifyItemRangeInserted(initPosition + 1, views.size)
        }

        if(views.isEmpty() || views.size < itemPerPage)
            disableLoading()
    }

    fun clearData(){
        if(items.size > 1) {
            items.clear()
            items.add(loadingItem)
            notifyDataSetChanged()
        }
    }

    fun enableLoading() {
        items.add(loadingItem)
        notifyItemChanged(items.size - 1)
    }

    fun disableLoading() {
        if(items.size > 0 && items[items.size - 1].getViewType() == AdapterConstants.LOADING) {
            items.removeAt(items.size - 1)
            notifyItemRemoved(items.size - 1)
        }
    }

    fun enableLoadMore(recyclerView: RecyclerView, callback: LoadMoreCallback) {
        loadMoreCallback = callback

        recyclerView.addOnScrollListener(object : InfiniteScrollListener({

            if (currentPage < maxPage) {
                currentPage = currentPage.plus(1)
                Log.d("TEST909", "Current page " + currentPage)
//                enableLoading()
                loadMoreCallback?.addMoreData()
            } else {
                loadMoreCallback = null
            }

        }, { pos ->
            currentVisiblePosition = pos
            if(lastPositionUpdated < 0){
                lastPositionUpdated = currentVisiblePosition
//                scrollPositionObs.value = currentVisiblePosition
            }else{
                if(Math.abs(currentVisiblePosition - lastPositionUpdated) >= 5){
                    lastPositionUpdated = currentVisiblePosition
//                    scrollPositionObs.value = currentVisiblePosition
                }
            }
        }) {})
    }
}