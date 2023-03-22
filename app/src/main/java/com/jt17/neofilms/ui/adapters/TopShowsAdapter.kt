package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.ShowsItemBinding
import com.jt17.neofilms.models.Top250ShowsModel
import com.squareup.picasso.Picasso

class TopShowsAdapter : RecyclerView.Adapter<TopShowsAdapter.ItemHolder>() {
    inner class ItemHolder(val b: ShowsItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(result: Top250ShowsModel) {

            b.apply {
                rankingShows.text = result.rank
                showsName.text = result.title
                yearM.text = result.year
                crewShows.text = result.crew
                imdbRatingShows.text = result.imDbRating
            }

            Picasso.get().load(result.image)
                .error(R.drawable.movie_placeholder_img)
                .placeholder(R.drawable.movie_placeholder_img)
                .into(b.showsImg)

        }
    }

    var topShowsList = emptyList<Top250ShowsModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ShowsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = topShowsList[position]

        holder.bind(itemData)

    }

    override fun getItemCount(): Int = topShowsList.size


}