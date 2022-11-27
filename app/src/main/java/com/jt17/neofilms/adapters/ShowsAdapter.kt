package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.ShowsItemBinding
import com.jt17.neofilms.models.mainModel
import com.squareup.picasso.Picasso

class ShowsAdapter(val list: List<mainModel>) :
    RecyclerView.Adapter<ShowsAdapter.itemHolder>() {
    inner class itemHolder(val bind: ShowsItemBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): itemHolder {
        return itemHolder(
            ShowsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: itemHolder, position: Int) {
        val itemData = list[position]

        holder.bind.rankingShows.text = itemData.rank
        holder.bind.seriesName.text = itemData.title
        holder.bind.yearSh.text = itemData.year
        holder.bind.crewShows.text = itemData.crew
        holder.bind.imdbRatingShows.text = itemData.imDbRating

        Picasso.get().load(itemData.image).error(R.drawable.ic_launcher_background)
            .into(holder.bind.showsImg)

    }

    override fun getItemCount(): Int = list.size


}