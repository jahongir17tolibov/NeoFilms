package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.top250_moviesModel
import com.squareup.picasso.Picasso

class HomeAdapter(val lists: List<top250_moviesModel>) : RecyclerView.Adapter<HomeAdapter.itemHolder>() {
    inner class itemHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {
        val holder = itemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movies_item, parent, false)
        )
        return holder
    }

    override fun onBindViewHolder(holder: itemHolder, position: Int) {
        val itemData = lists[position]
        holder.itemView.findViewById<TextView>(R.id.ranking_movies).text = itemData.rank
        holder.itemView.findViewById<TextView>(R.id.film_name).text = itemData.title
        holder.itemView.findViewById<TextView>(R.id.imdbRating_movies).text = itemData.imDbRating
        holder.itemView.findViewById<TextView>(R.id.year_m).text = itemData.year
        holder.itemView.findViewById<TextView>(R.id.crew_movies).text = itemData.crew

        Picasso.get().load(itemData.image).error(R.drawable.ic_launcher_background).into(holder.itemView.findViewById<ImageView>(R.id.movies_img))
    }

    override fun getItemCount(): Int = lists.size
}