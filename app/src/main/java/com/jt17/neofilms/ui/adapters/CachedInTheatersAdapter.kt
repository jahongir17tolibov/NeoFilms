package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.ui.screens.TopMoviesFragmentDirections
import com.squareup.picasso.Picasso

class CachedInTheatersAdapter : RecyclerView.Adapter<CachedInTheatersAdapter.ItemHolder>() {

    var baselist = emptyList<InTheatresModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: MoviesItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: InTheatresModel) {
            b.yearM.text = result.year
            b.imdbRatingMovies.text = result.imDbRating
            b.viewForRank.isVisible = false
            b.filmName.text = result.title
            b.crewMovies.text = result.plot

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

    private var onItemClickListener: ((InTheatresModel) -> Unit)? = null

    fun setOnFavItemClickListener(listener: ((InTheatresModel) -> Unit)? = null) {
        onItemClickListener = listener
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

    override fun getItemCount(): Int = baselist.size

}