package br.com.kotlinhub.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class InfiniteScrollListener(
    private val layoutManager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private val visibleThreshold = 5
    private var previousTotalItemCount = 0
    private var isLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            val totalItemCount = layoutManager.itemCount

            if (isLoading && totalItemCount > previousTotalItemCount) {
                isLoading = false
                previousTotalItemCount = totalItemCount
            }

            if (!isLoading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
                onLoadMore()
                isLoading = true
            }
        }
    }

    abstract fun onLoadMore()
}