package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.InthItemBinding
import com.jt17.neofilms.models.InTheatresModel
import com.squareup.picasso.Picasso

class InTheatersAdapter : RecyclerView.Adapter<InTheatersAdapter.ItemHolder>() {

    private var onItemClickListener: ((InTheatresModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((InTheatresModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class ItemHolder(val b: InthItemBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: InTheatresModel) {

            b.TitleInTh.text = result.title
            b.releaseData.text = result.releaseState
            b.genres.text = result.genres

            Picasso.get().load(result.image).resize(110, 160)
                .placeholder(R.drawable.movie_placeholder_img)
                .onlyScaleDown().error(R.color.black).into(b.inThImage)
        }
    }

    var baseList = emptyList<InTheatresModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            InthItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

    override fun getItemCount(): Int = baseList.size

}