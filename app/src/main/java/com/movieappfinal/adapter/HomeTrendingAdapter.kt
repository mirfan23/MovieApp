package com.movieappfinal.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataTrendingMovie
import com.movieappfinal.core.domain.model.DataTrendingMovieItem
import com.movieappfinal.databinding.HomeItemBinding
import com.movieappfinal.utils.SpaceItemDecoration

class HomeTrendingAdapter(
    private val action: (DataTrendingMovieItem) -> Unit,
) : BaseListAdapter<DataTrendingMovie, HomeItemBinding>(
    HomeItemBinding::inflate
) {
    override fun onItemBind(): (DataTrendingMovie, HomeItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                val homeTrendingItemAdapter = HomeTrendingItemAdapter(action)
                tvSubTitle.text = root.context.getString(R.string.trending_title)
                rvItem.apply {
                    layoutManager =
                        LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = homeTrendingItemAdapter
                    val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                    addItemDecoration(SpaceItemDecoration(spaceInPixels))
                }
                homeTrendingItemAdapter.submitList(item.items)
            }
        }

}