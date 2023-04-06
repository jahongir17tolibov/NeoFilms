package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.CastItemLyBinding
import com.jt17.neofilms.models.BoxOfficeModel
import com.jt17.neofilms.models.TitleModel
import com.squareup.picasso.Picasso

class CastAdapter : RecyclerView.Adapter<CastAdapter.ItemHolder>() {

    var baseList = emptyList<TitleModel.ActorsModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: CastItemLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: TitleModel.ActorsModel) = b.apply {
            actorsName.text = result.name
            actorsAsCharacter.text = result.asCharacter

            Picasso.get().load(result.image)
                .placeholder(R.color.black)
                .resize(260, 360)
                .onlyScaleDown()
                .error(R.color.black)
                .into(actorsImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        CastItemLyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)

    }

    override fun getItemCount(): Int = baseList.size

}