package com.movieappfinal.adapter

import android.view.View
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.core.domain.model.DataTrendingMovieItem
import com.movieappfinal.databinding.HomeMovieItemBinding
import com.movieappfinal.utils.Constant
import kotlin.random.Random

class HomeTrendingItemAdapter(private val action: (DataTrendingMovieItem) -> Unit) :
    BaseListAdapter<DataTrendingMovieItem, HomeMovieItemBinding>(HomeMovieItemBinding::inflate) {

    override fun onItemBind(): (DataTrendingMovieItem, HomeMovieItemBinding, View, Int) -> Unit =
        { item, binding, itemView, _ ->
            val randomMultiplier = Random.nextInt(1,20)
            val moviePrice = randomMultiplier * 50
            binding.run {
                ivMovie.load(Constant.Img_Url + item.poster)
                tvMovieTitle.text = item.title
                tvReleaseDate.text = item.releaseDate
                tvToken.text = item.popularity.toString()
            }
            itemView.setOnClickListener {
                action.invoke(item)
            }
        }
}