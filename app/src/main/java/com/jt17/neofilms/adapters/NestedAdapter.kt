package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.InthItemBinding
import com.jt17.neofilms.models.NestedModel
import com.squareup.picasso.Picasso

class NestedAdapter(val list: List<NestedModel.InTheatresModel>) :
    RecyclerView.Adapter<NestedAdapter.nestedHolder>() {

    inner class nestedHolder(val bind: InthItemBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): nestedHolder {
        return nestedHolder(
            InthItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: nestedHolder, position: Int) {
        val itemData = list[position]
        holder.bind.TitleInTh.text = itemData.title
        holder.bind.releaseData.text = itemData.releaseState
        holder.bind.genres.text = itemData.genres

        Picasso.get().load(itemData.image).error(R.color.black).into(holder.bind.inThImage)
    }

    override fun getItemCount(): Int = list.size

}