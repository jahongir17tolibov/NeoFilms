package com.jt17.neofilms.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesItemBinding
import com.jt17.neofilms.models.mainModel
import com.squareup.picasso.Picasso

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.itemHolder>() {
    inner class itemHolder(val bind: MoviesItemBinding) : RecyclerView.ViewHolder(bind.root)

    var baselist: List<mainModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun TopMlist(list: List<mainModel>) {
        baselist = list
        notifyDataSetChanged()
    }

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
        val itemData = baselist[position]

        with(itemData) {
            holder.bind.rankingMovies.text = rank
            holder.bind.filmName.text = title
            holder.bind.imdbRatingMovies.text = imDbRating
            holder.bind.yearM.text = year
            holder.bind.crewMovies.text = crew

            Picasso.get().load(image)
                .error(R.drawable.ic_launcher_background)
                .into(holder.bind.moviesImg)
        }

//        holder.bind.materialed1.setOnClickListener {
//            itemCallback.itemClickListener(position)
//        }
    }

    override fun getItemCount(): Int = baselist.size

}
//interface ItemCallback{
//    fun itemClickListener(position: Int)
//}