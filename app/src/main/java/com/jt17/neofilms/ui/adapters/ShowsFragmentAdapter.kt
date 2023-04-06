package com.jt17.neofilms.ui.adapters

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.MoviesFragmentItemsLyBinding
import com.jt17.neofilms.models.MoviesModel
import com.jt17.neofilms.models.ShowsModel
import com.jt17.neofilms.ui.screens.ShowsFragmentDirections
import com.squareup.picasso.Picasso

class ShowsFragmentAdapter : RecyclerView.Adapter<ShowsFragmentAdapter.ItemHolder>() {

    var baseList = emptyList<ShowsModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: MoviesFragmentItemsLyBinding) : RecyclerView.ViewHolder(b.root) {

        fun bind(result: ShowsModel) {
            val imageViews = listOf(b.img1, b.img2, b.img3, b.img4)
            b.mainBackgroundImage.setImageResource(
                result.mainImg ?: R.drawable.movie_placeholder_img
            )

            for (i in 0 until 4) {
                val image = result.imagesList?.getOrNull(i)
                if (image != null) {
                    Picasso.get().load(image)
                        .placeholder(R.drawable.movie_placeholder_img)
                        .resize(180, 260)
                        .onlyScaleDown()
                        .error(R.color.black)
                        .into(imageViews[i])
                } else {
                    imageViews[i].setImageResource(R.drawable.movie_placeholder_img)
                }
            }

            when (result.titleTxt) {
                "Top 250 TV shows sorted by ImDb" -> {
                    b.mainFrame.isVisible = false
                    b.secMainText.apply {
                        isVisible = true
                        text = result.titleTxt
                    }
                }
                "My favourite TV shows" -> {
                    b.mainFrame.isVisible = false
                    b.secMainText.apply {
                        isVisible = true
                        text = result.titleTxt
                    }
                }
                "TV shows that are popular now" -> b.categoryName.text = result.titleTxt
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder = ItemHolder(
        MoviesFragmentItemsLyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]

        holder.bind(itemData)

        holder.b.mainBackgroundImage.setRenderEffect(
            RenderEffect.createBlurEffect(
                25f,
                25f,
                Shader.TileMode.MIRROR
            )
        )

        holder.b.secMainText.also {
            if (it.text == "My favourite movies") {
                it.setTextColor(R.color.md_theme_light_onPrimaryContainer)
            }
        }

        holder.itemView.setOnClickListener {

            when (holder.b.secMainText.text) {
                "Top 250 TV shows sorted by ImDb" -> {
                    val topShowsAction =
                        ShowsFragmentDirections.actionShowsFragmentToTopShowsFragment(
                            holder.b.secMainText.text.toString()
                        )
                    it.findNavController().navigate(topShowsAction)
                }
                "My favourite TV shows" -> {
                    val favShowsAction =
                        ShowsFragmentDirections.actionShowsFragmentToFavSeriesFragment()
                    it.findNavController().navigate(favShowsAction)
                }
            }

            if (holder.b.categoryName.text == "TV shows that are popular now") {
                val action = ShowsFragmentDirections.actionShowsFragmentToTopShowsFragment(
                    holder.b.categoryName.text.toString()
                )
                it.findNavController().navigate(action)
            }

        }

    }

    override fun getItemCount(): Int = baseList.size

}