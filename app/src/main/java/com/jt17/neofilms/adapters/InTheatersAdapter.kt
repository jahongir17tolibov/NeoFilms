package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.ModalBottomSheet
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.InthItemBinding
import com.jt17.neofilms.models.InTheatresModel
import com.squareup.picasso.Picasso

class InTheatersAdapter(val itemCallback: ItemCallback) : RecyclerView.Adapter<InTheatersAdapter.ItemHolder>() {

    inner class ItemHolder(val bind: InthItemBinding) : RecyclerView.ViewHolder(bind.root) {

        fun binder(result: InTheatresModel) {

            bind.TitleInTh.text = result.title
            bind.releaseData.text = result.releaseState
            bind.genres.text = result.genres

            Picasso.get().load(result.image).error(R.color.black).into(bind.inThImage)
        }
    }

    var baseList: List<InTheatresModel> = emptyList()

    fun newList(list: List<InTheatresModel>) {
        baseList = list
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

        holder.binder(itemData)


        holder.itemView.setOnClickListener {
            itemCallback.putBottomSheet(itemData)
        }

    }

    override fun getItemCount(): Int = baseList.size

}

interface ItemCallback {
    fun putBottomSheet(itemData: InTheatresModel)
}