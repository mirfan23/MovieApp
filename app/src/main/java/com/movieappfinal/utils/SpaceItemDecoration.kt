package com.movieappfinal.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private  val space: Int): RecyclerView.ItemDecoration()  {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val itemSize = state.itemCount
        when  {
            itemSize % 2 == 0 -> {outRect.right = space}
        }
        outRect.bottom = space
    }
}
