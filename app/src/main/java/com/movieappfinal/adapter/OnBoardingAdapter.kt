package com.movieappfinal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.movieappfinal.R
import com.movieappfinal.databinding.OnBoardingItemBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

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

    private val description = arrayOf(
        R.string.onboarding_1_desc,
        R.string.onboarding_2_desc,
        R.string.onboarding_3_desc
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = OnBoardingItemBinding.inflate(inflater, parent, false)
        return OnBoardingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        val currentImage  = image[position]
        val currentTitle = holder.itemView.context.getString(title[position])
        val currentDesc = holder.itemView.context.getString(description[position])
        holder.bind(currentImage, currentTitle, currentDesc)
    }
    override fun getItemCount(): Int {
        return image.size
    }

    inner class OnBoardingViewHolder(private val binding: OnBoardingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: Int, title: String, desc: String) {
            binding.ivOnBoarding.setImageResource(image)
            binding.tvTitleOnBoarding.text = title
            binding.tvMiniDesc.text = desc
        }
    }
}