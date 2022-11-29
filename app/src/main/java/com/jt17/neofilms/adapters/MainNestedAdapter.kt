package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.databinding.InthParentItemBinding
import com.jt17.neofilms.models.NestedModel

class MainNestedAdapter(val collection: List<NestedModel>) :
    RecyclerView.Adapter<MainNestedAdapter.mainHolder>() {

    inner class mainHolder(val bind: InthParentItemBinding) : RecyclerView.ViewHolder(bind.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mainHolder {
        return mainHolder(
            InthParentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: mainHolder, position: Int) {
        val itemData = collection[position]
        holder.bind.whatIsIt.text = itemData.titleName
        val titleAdapter = NestedAdapter(itemData.modelList)
        holder.bind.RecycInthChild.adapter = titleAdapter
    }

    override fun getItemCount(): Int = collection.size

}