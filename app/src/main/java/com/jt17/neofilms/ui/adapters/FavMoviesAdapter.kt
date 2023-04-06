package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.BoxOfficeModel
import com.jt17.neofilms.models.FavMoviesModel
import com.squareup.picasso.Picasso

class FavMoviesAdapter :
    ListAdapter<FavMoviesModel, FavMoviesAdapter.ItemHolder>(FavMoviesDiffUtil()) {

    inner class ItemHolder(val b: MoviesItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: FavMoviesModel) = b.apply {
            addToFavBtn.isVisible = false
            viewForRank.isVisible = false
            crewMovies.text = result.crew
            filmName.text = result.title
            imdbRatingMovies.text = result.imDbRating
            yearM.text = result.year

            Picasso.get().load(result.image)
                .placeholder(R.drawable.movie_placeholder_img)
                .resize(210, 300)
                .onlyScaleDown()
                .error(R.color.black)
                .into(moviesImg)

        }

    }

    internal class FavMoviesDiffUtil : DiffUtil.ItemCallback<FavMoviesModel>() {
        override fun areItemsTheSame(oldItem: FavMoviesModel, newItem: FavMoviesModel): Boolean {
            return oldItem.movieId == newItem.movieId
        }

        override fun areContentsTheSame(oldItem: FavMoviesModel, newItem: FavMoviesModel): Boolean =
            areItemsTheSame(oldItem, newItem)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = getItem(position)

        holder.bind(itemData)

        holder.itemView.setOnLongClickListener {
            onItemClickListener?.invoke(itemData)
            true
        }

    }

    private var onItemClickListener: ((FavMoviesModel) -> Unit)? = null

    fun setOnLongItemClickListener(listener: ((FavMoviesModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

}