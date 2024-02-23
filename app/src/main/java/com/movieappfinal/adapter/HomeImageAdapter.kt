package com.movieappfinal.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.core.base.BaseListAdapter
import com.movieappfinal.R
import com.movieappfinal.core.remote.data.DataDummy
import com.movieappfinal.databinding.HomeMovieItemBinding
import com.movieappfinal.databinding.HomeRecyclerItemBinding

class HomeImageAdapter(private val action:(DataDummy) -> Unit): BaseListAdapter<DataDummy, HomeMovieItemBinding>(HomeMovieItemBinding::inflate){

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

    override fun onItemBind(): (DataDummy, HomeMovieItemBinding, View, Int) -> Unit =
    { item, binding, itemView, _ ->
        binding.run {
            ivMovie.load(image)
            tvMovieTitle.text = title.toString()
        }
        itemView.setOnClickListener {
            action.invoke(item)
        }
    }

}