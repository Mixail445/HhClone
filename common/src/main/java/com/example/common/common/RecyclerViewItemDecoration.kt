package com.example.common.common

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration(
    private val space: Int = 32,
    private val horizontal: Boolean = true,
    private val vertical: Boolean = true,
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val totalCount = parent.adapter!!.itemCount

        if (horizontal) {
            when (itemPosition) {
                0 -> {
                    outRect.right = space
                }
                totalCount - 1 -> {
                    outRect.left = space
                }
                else -> {
                    outRect.left = space / 2
                    outRect.right = space / 2
                }
            }
        }
        if (vertical) {
            outRect.top = space / 2
            outRect.bottom = space / 2
        }
    }
}
