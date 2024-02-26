package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.movieappfinal.R
import com.movieappfinal.core.domain.model.DataPopularMovieItem
import com.movieappfinal.databinding.HomeRecyclerItemBinding
import com.movieappfinal.databinding.OnBoardingItemBinding
import com.movieappfinal.utils.Constant.Img_Url
import com.movieappfinal.utils.Constant.Img_Url_Original

class HomeCarouselAdapter(private val movieList: List<DataPopularMovieItem>) : RecyclerView.Adapter<HomeCarouselAdapter.HomeCarouselViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeRecyclerItemBinding.inflate(inflater, parent, false)
        return HomeCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        val movie  = movieList[position]
        holder.bind(movie)
    }
    override fun getItemCount(): Int {
        return 3
    }

    inner class HomeCarouselViewHolder(private val binding: HomeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: DataPopularMovieItem) {
            binding.ivCarousel.load(Img_Url_Original+movie.backdrop)
            binding.tvTitleCarousel.text = movie.title
            binding.tvGenre.text = movie.genreIds.toString()
        }
    }
}