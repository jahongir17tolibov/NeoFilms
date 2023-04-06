package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.BoxOfficeItemLyBinding
import com.jt17.neofilms.models.BoxOfficeModel
import com.squareup.picasso.Picasso

class BoxOfficeAdapter : RecyclerView.Adapter<BoxOfficeAdapter.ItemHolder>() {

    var baseList = emptyList<BoxOfficeModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: BoxOfficeItemLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: BoxOfficeModel) {

            b.boxOffTitle.text = result.title
            b.boxOffGross.text = result.gross
            b.boxOffWeekend.text = result.weekend
            b.boxOffWeeks.text = result.weeks

            Picasso.get().load(result.image)
                .resize(220, 320)
                .placeholder(R.drawable.movie_placeholder_img)
                .onlyScaleDown()
                .error(R.color.black)
                .into(b.boxOffImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            BoxOfficeItemLyBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)


        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.alpha_anim)

    }


    private var onItemClickListener: ((BoxOfficeModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((BoxOfficeModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int = baseList.size

}