package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movieappfinal.R
import com.movieappfinal.databinding.OnBoardingItemBinding

class HomeCarouselAdapter : RecyclerView.Adapter<HomeCarouselAdapter.HomeCarouselAdapter>() {

    private val image = arrayOf(
        R.drawable.on_boarding_1,
        R.drawable.on_boarding_2,
        R.drawable.on_boarding_3
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeCarouselAdapter {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnBoardingItemBinding.inflate(inflater, parent, false)
        return HomeCarouselAdapter(binding)
    }

    override fun onBindViewHolder(holder: HomeCarouselAdapter, position: Int) {
        val currentImage  = image[position]
        val currentTitle = holder.itemView.context.getString(title[position])
        val currentDesc = holder.itemView.context.getString(genre[position])
        holder.bind(currentImage, currentTitle, currentDesc)
    }
    override fun getItemCount(): Int {
        return image.size
    }

    inner class HomeCarouselAdapter(private val binding: OnBoardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, title: String, desc: String) {
            binding.ivOnBoarding.setImageResource(image)
            binding.tvTitleOnBoarding.text = title
            binding.tvMiniDesc.text = desc
        }
    }
}