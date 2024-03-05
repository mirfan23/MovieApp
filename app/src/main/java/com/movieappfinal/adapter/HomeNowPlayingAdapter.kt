package com.movieappfinal.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataNowPlaying
import com.movieappfinal.core.domain.model.DataNowPlayingItem
import com.movieappfinal.databinding.HomeItemBinding
import com.movieappfinal.utils.SpaceItemDecoration

class HomeNowPlayingAdapter(
    private val action: (DataNowPlayingItem) -> Unit,
) : BaseListAdapter<DataNowPlaying, HomeItemBinding>(
    HomeItemBinding::inflate
) {
    override fun onItemBind(): (DataNowPlaying, HomeItemBinding, View, Int) -> Unit =
        { item, binding, _, _ ->
            binding.run {
                val homeNowPlayingItemAdapter = HomeNowPlayingItemAdapter(action)
                tvSubTitle.text = root.context.getString(R.string.now_playing_title)
                rvItem.apply {
                    layoutManager =
                        LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                    adapter = homeNowPlayingItemAdapter
                    val spaceInPixels = resources.getDimensionPixelSize(R.dimen.item_spacing)
                    addItemDecoration(SpaceItemDecoration(spaceInPixels))
                }
                homeNowPlayingItemAdapter.submitList(item.items)
            }
        }
}
