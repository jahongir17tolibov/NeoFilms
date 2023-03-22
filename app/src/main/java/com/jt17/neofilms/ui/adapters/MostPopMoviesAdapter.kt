package com.jt17.neofilms.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.PopularMoviesItemBinding
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.MostPopMoviesModel
import com.squareup.picasso.Picasso

class MostPopMoviesAdapter : RecyclerView.Adapter<MostPopMoviesAdapter.ItemHolder>() {

    private var onItemClickListener: ((MostPopMoviesModel) -> Unit)? = null

    fun setOnItemClickListener(listener: ((MostPopMoviesModel) -> Unit)? = null) {
        onItemClickListener = listener
    }

    inner class ItemHolder(val b: PopularMoviesItemBinding) : RecyclerView.ViewHolder(b.root) {
        fun bind(result: MostPopMoviesModel) {

            b.popMoviesRankUpDown.text = result.rankUpDown
            b.popMoviesTitle.text = result.title
            b.popMoviesYear.text = result.year

            Picasso.get()
                .load(result.image)
                .resize(110, 160)
                .onlyScaleDown()
                .placeholder(R.drawable.movie_placeholder_img)
                .error(R.color.black)
                .into(b.popMoviesImage)
        }
    }

    var baseList = emptyList<MostPopMoviesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            PopularMoviesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)

        upDownSigns(holder)//-> Up Equal Down signs for rank (ImageView)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(itemData)
        }

    }

    override fun getItemCount(): Int = baseList.size

    private fun upDownSigns(holder: ItemHolder) {
        val upDown = holder.b.popMoviesRankUpDown.text.toString()
        if (upDown.toInt() < 0) {
            holder.b.upgrade.visibility = View.GONE
            holder.b.downgrade.visibility = View.VISIBLE
            holder.b.equality.visibility = View.GONE
        } else if (upDown.toInt() == 0) {
            holder.b.upgrade.visibility = View.GONE
            holder.b.downgrade.visibility = View.GONE
            holder.b.equality.visibility = View.VISIBLE
        } else if (upDown.toInt() > 0) {
            holder.b.upgrade.visibility = View.VISIBLE
            holder.b.downgrade.visibility = View.GONE
            holder.b.equality.visibility = View.GONE
        }
    }

}
