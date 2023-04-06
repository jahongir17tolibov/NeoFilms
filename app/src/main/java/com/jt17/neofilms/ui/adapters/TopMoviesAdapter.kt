package com.jt17.neofilms.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.Top250MoviesModel
import com.jt17.neofilms.ui.screens.TopMoviesFragmentDirections
import com.squareup.picasso.Picasso

class TopMoviesAdapter : RecyclerView.Adapter<TopMoviesAdapter.ItemHolder>() {
    inner class ItemHolder(val b: MoviesItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: Top250MoviesModel) {
            b.rankingMovies.text = result.rank
            b.filmName.text = result.title
            b.imdbRatingMovies.text = result.imDbRating
            b.yearM.text = result.year
            b.crewMovies.text = result.crew

            Picasso.get().load(result.image)
                .error(R.drawable.movie_placeholder_img)
                .placeholder(R.drawable.movie_placeholder_img)
                .into(b.moviesImg)
        }

    }

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

        holder.bind(itemData)

        holder.itemView.setOnClickListener {
            val action =
                TopMoviesFragmentDirections.actionTopMoviesFragmentToMainInfoFragment(
                    itemData.id,
                    false
                )
            it.findNavController().navigate(action)
        }

        holder.b.addToFavBtn.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

    private var onItemClickListener: ((Top250MoviesModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((Top250MoviesModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = baselist.size

}