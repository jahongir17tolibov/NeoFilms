package com.jt17.neofilms.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.databinding.InthParentItemBinding
import com.jt17.neofilms.models.NestedModel

class MainNestedAdapter(private val collection: List<NestedModel>) :
    RecyclerView.Adapter<MainNestedAdapter.MainHolder>() {

    inner class MainHolder(val bind: InthParentItemBinding) : RecyclerView.ViewHolder(bind.root) {
        fun binder(nestedModel: NestedModel) {
            bind.whatIsIt.text = nestedModel.titleName
            val mainNest = NestedAdapter(nestedModel.modelList)
            bind.RecycInthChild.run {
                layoutManager =
                    LinearLayoutManager(bind.root.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mainNest
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            InthParentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val itemData = collection[position]
        holder.binder(itemData)
    }

    override fun getItemCount(): Int = collection.size

}