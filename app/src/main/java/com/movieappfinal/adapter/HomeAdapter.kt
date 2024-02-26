package com.movieappfinal.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataPopularMovie
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.databinding.HomeItemBinding
import com.movieappfinal.utils.SpaceItemDecoration

class HomeAdapter(private val action: (DataPopularMovieItem) -> Unit): BaseListAdapter<DataPopularMovie, HomeItemBinding>(
    HomeItemBinding::inflate){

    override fun onItemBind(): (DataPopularMovie, HomeItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                val homeImageAdapter = HomeImageAdapter(action)
                tvSubTitle.text = item.title
                rvItem.apply {
                    layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = homeImageAdapter
                    val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                    addItemDecoration(SpaceItemDecoration(spaceInPixels))
                }
                homeImageAdapter.submitList(item.items)
            }
        }

}