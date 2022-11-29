package com.jt17.neofilms.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.squareup.picasso.Picasso

class HomeAdapter(val lists: List<mainModel>) :
    RecyclerView.Adapter<HomeAdapter.itemHolder>() {
    inner class itemHolder(val bind: MoviesItemBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {
        return itemHolder(
            MoviesItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: itemHolder, position: Int) {
        val itemData = lists[position]

        holder.bind.rankingMovies.text = itemData.rank
        holder.bind.filmName.text = itemData.title
        holder.bind.imdbRatingMovies.text = itemData.imDbRating
        holder.bind.yearM.text = itemData.year
        holder.bind.crewMovies.text = itemData.crew

        Picasso.get().load(itemData.image)
            .error(R.drawable.ic_launcher_background)
            .into(holder.bind.moviesImg)

//        holder.bind.materialed1.setOnClickListener {
//            itemCallback.itemClickListener(position)
//        }
    }

    override fun getItemCount(): Int = lists.size

}
interface ItemCallback{
    fun itemClickListener(position: Int)
}