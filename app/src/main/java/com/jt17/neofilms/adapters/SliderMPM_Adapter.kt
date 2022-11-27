package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.PopularMoviesItemBinding
import com.jt17.neofilms.models.mainModel
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderMPM_Adapter(private val list: List<mainModel>) :
    SliderViewAdapter<SliderMPM_Adapter.itemHolder>() {

    inner class itemHolder(val bind: PopularMoviesItemBinding) :
        SliderViewAdapter.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup?): itemHolder {
        return itemHolder(
            PopularMoviesItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: itemHolder?, position: Int) {
        val itemdata = list[position]
        viewHolder?.bind?.popularMTitle?.text = itemdata.title
        viewHolder?.bind?.imdbRatingPopularM?.text = itemdata.imDbRating
        viewHolder?.bind?.popularMYear?.text = itemdata.year

        val titleImage = Picasso.get().load(itemdata.image).error(R.drawable.ic_launcher_background)
            .into(viewHolder?.bind?.popularMImage)
    }

    override fun getCount(): Int = list.size

}
