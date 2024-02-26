package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieappfinal.R
import com.movieappfinal.databinding.HomeRecyclerItemBinding
import com.movieappfinal.databinding.OnBoardingItemBinding

class HomeCarouselAdapter : RecyclerView.Adapter<HomeCarouselAdapter.HomeCarouselViewHolder>() {

    private val image = arrayOf(
        R.drawable.carousel_home,
        R.drawable.carousel_home,
        R.drawable.carousel_home
    )

    private val title = arrayOf(
        R.string.onboarding_1_title,
        R.string.onboarding_2_title,
        R.string.onboarding_3_title
    )

    private val genre = arrayOf(
        R.string.action_drama_genre,
        R.string.action_drama_genre,
        R.string.action_drama_genre
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HomeRecyclerItemBinding.inflate(inflater, parent, false)
        return HomeCarouselViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselViewHolder, position: Int) {
        val currentImage  = image[position]
        val currentTitle = holder.itemView.context.getString(title[position])
        val currentDesc = holder.itemView.context.getString(genre[position])
        holder.bind(currentImage, currentTitle, currentDesc)
    }
    override fun getItemCount(): Int {
        return image.size
    }

    inner class HomeCarouselViewHolder(private val binding: HomeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, title: String, genre: String) {
            binding.ivCarousel.setImageResource(image)
            binding.tvTitleCarousel.text = title
            binding.tvGenre.text = genre
        }
    }
}
