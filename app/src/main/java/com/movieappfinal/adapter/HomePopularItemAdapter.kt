package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.databinding.HomeMovieItemBinding
import com.movieappfinal.utils.Constant.Img_Url

class HomePopularItemAdapter(private val action: (DataPopularMovieItem) -> Unit) :
    BaseListAdapter<DataPopularMovieItem, HomeMovieItemBinding>(HomeMovieItemBinding::inflate) {

    override fun onItemBind(): (DataPopularMovieItem, HomeMovieItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            binding.run {
                ivMovie.load(Img_Url + item.poster)
                tvMovieTitle.text = item.title
                tvReleaseDate.text = item.releaseDate
                tvToken.text = item.popularity.toString()
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}
