package com.movieappfinal.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.databinding.HomeItemBinding
import com.movieappfinal.utils.SpaceItemDecoration

class HomePopularAdapter(
    private val popular: (DataPopularMovieItem) -> Unit,
) : BaseListAdapter<DataPopularMovie, HomeItemBinding>(
    HomeItemBinding::inflate
) {
    override fun onItemBind(): (DataPopularMovie, HomeItemBinding, View, Int) -> Unit =
        { item, binding, _, _ ->
            binding.run {
                val homePopularItemAdapter = HomePopularItemAdapter(popular)
                tvSubTitle.text = root.context.getString(R.string.popular_title)
                rvItem.apply {
                    layoutManager =
                        LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = homePopularItemAdapter
                    val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                    addItemDecoration(SpaceItemDecoration(spaceInPixels))
                }
                homePopularItemAdapter.submitList(item.itemsPopular)
            }
        }

}
