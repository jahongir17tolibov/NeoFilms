package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.Top250MoviesModel
import com.squareup.picasso.Picasso

class TopMoviesAdapter : RecyclerView.Adapter<TopMoviesAdapter.ItemHolder>() {
    inner class ItemHolder(val b: MoviesItemBinding) : RecyclerView.ViewHolder(b.root)

    var baselist = emptyList<Top250MoviesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baselist[position]

        with(itemData) {
            holder.b.rankingMovies.text = rank
            holder.b.filmName.text = title
            holder.b.imdbRatingMovies.text = imDbRating
            holder.b.yearM.text = year
            holder.b.crewMovies.text = crew

            Picasso.get().load(image)
                .error(R.drawable.movie_placeholder_img)
                .placeholder(R.drawable.movie_placeholder_img)
                .resize(90, 130)
                .onlyScaleDown()
                .into(holder.b.moviesImg)
        }
    }

    override fun getItemCount(): Int = baselist.size

}