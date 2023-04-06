package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.ComingMoviesItemBinding
import com.jt17.neofilms.models.ComingSoonModel
import com.jt17.neofilms.models.MostPopTVShowsModel
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter : SliderViewAdapter<SliderAdapter.ItemHolder>() {

    private var onItemClickListener: ((ComingSoonModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((ComingSoonModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    var comingSoonBaseList = emptyList<ComingSoonModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: ComingMoviesItemBinding) : ViewHolder(b.root) {

        fun bind(result: ComingSoonModel) {
            b.comingSoonTitle.text = result.title
            b.comingSoonGenre.text = result.genres
            b.comingSoonRelease.text = result.releaseState

            Picasso.get().load(result.image)
                .resize(220, 320)
                .onlyScaleDown()
                .error(R.color.black)
                .placeholder(R.drawable.movie_placeholder_img)
                .into(b.comingSoonImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): ItemHolder {
        return ItemHolder(
            ComingMoviesItemBinding.inflate(
                LayoutInflater.from(parent?.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(viewHolder: ItemHolder?, position: Int) {
        val itemData = comingSoonBaseList[position]

        viewHolder?.bind(itemData)

        viewHolder?.itemView?.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

    override fun getCount(): Int = comingSoonBaseList.size

}
