package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.movieappfinal.core.domain.model.DataSearchMovie
import com.movieappfinal.core.utils.BasePagingAdapter
import com.movieappfinal.databinding.SearchItemBinding
import com.movieappfinal.utils.Constant

class SearchAdapter(
    private val action: (DataSearchMovie) -> Unit,
) : BasePagingAdapter<DataSearchMovie, SearchItemBinding>(
    SearchItemBinding::inflate
) {
    override fun onItemBind(): (DataSearchMovie, SearchItemBinding, View, Int) -> Unit =
        { item, binding, _, _ ->
            binding.apply {
                ivMoviePoster.load(Constant.Img_Url + item.poster)
                tvMovieName.text = item.title
                tvMovieRelease.text = item.releaseDate
                tvMoviePrice.text = item.popularity.toString()
                root.setOnClickListener {
                    action.invoke(item)
                }
            }
        }
}
