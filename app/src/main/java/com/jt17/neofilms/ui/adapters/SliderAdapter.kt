package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.ComingMoviesItemBinding
import com.jt17.neofilms.models.ComingSoonModel
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter : SliderViewAdapter<SliderAdapter.ItemHolder>() {

    inner class ItemHolder(val bind: ComingMoviesItemBinding) : ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup?): ItemHolder {
        return ItemHolder(
            ComingMoviesItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }

    var comingSoonBaseList = emptyList<ComingSoonModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(viewHolder: ItemHolder?, position: Int) {
        val itemData = comingSoonBaseList[position]
        viewHolder?.bind?.comingSoonTitle?.text = itemData.title
        viewHolder?.bind?.comingSoonGenre?.text = itemData.genres
        viewHolder?.bind?.comingSoonRelease?.text = itemData.releaseState

        Picasso.get().load(itemData.image)
            .resize(90, 130)
            .onlyScaleDown()
            .error(R.drawable.movie_placeholder_img)
            .placeholder(R.drawable.movie_placeholder_img)
            .into(viewHolder?.bind?.comingSoonImage)
    }

    override fun getCount(): Int = comingSoonBaseList.size

}
