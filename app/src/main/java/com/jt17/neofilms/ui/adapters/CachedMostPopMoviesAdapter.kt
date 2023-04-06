package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.BoxOfficeModel
import com.jt17.neofilms.models.MostPopMoviesModel
import com.jt17.neofilms.models.MostPopTVShowsModel
import com.jt17.neofilms.ui.screens.TopMoviesFragmentDirections
import com.squareup.picasso.Picasso

class CachedMostPopMoviesAdapter : RecyclerView.Adapter<CachedMostPopMoviesAdapter.ItemHolder>() {

    var baselist = emptyList<MostPopMoviesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: MoviesItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: MostPopMoviesModel) {
            b.viewForRank.isVisible = false
            b.filmName.text = result.title
            b.imdbRatingMovies.text = result.imDbRating
            b.crewMovies.text = result.crew
            b.yearM.text = result.year

            Picasso.get().load(result.image)
                .error(R.color.black)
                .resize(210, 300)
                .onlyScaleDown()
                .placeholder(R.drawable.movie_placeholder_img)
                .into(b.moviesImg)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        MoviesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    private var onItemClickListener: ((MostPopMoviesModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((MostPopMoviesModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baselist[position]

        holder.bind(itemData)

        holder.itemView.setOnClickListener {
            val action = TopMoviesFragmentDirections.actionTopMoviesFragmentToMainInfoFragment(
                itemData.id,
                false
            )
            it.findNavController().navigate(action)
        }

        holder.b.addToFavBtn.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

    override fun getItemCount(): Int = baselist.size

}