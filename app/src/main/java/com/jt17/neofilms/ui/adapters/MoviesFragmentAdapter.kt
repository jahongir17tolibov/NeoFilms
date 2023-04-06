package com.jt17.neofilms.ui.adapters

import android.annotation.SuppressLint
import android.content.res.ColorStateList
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
import com.jt17.neofilms.ui.screens.MoviesFragmentDirections
import com.jt17.neofilms.utils.BaseUtils
import com.squareup.picasso.Picasso

class MoviesFragmentAdapter : RecyclerView.Adapter<MoviesFragmentAdapter.ItemHolder>() {

    var baseList = emptyList<MoviesModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ItemHolder(val b: MoviesFragmentItemsLyBinding) : RecyclerView.ViewHolder(b.root) {

        @RequiresApi(Build.VERSION_CODES.S)
        fun bind(result: MoviesModel) {
            val imageViews = listOf(b.img1, b.img2, b.img3, b.img4)
            b.categoryName.text = result.titleTxt
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
                "Top 250 movies sorted by ImDb" -> {
                    b.mainFrame.isVisible = false
                    b.secMainText.apply {
                        isVisible = true
                        text = result.titleTxt
                    }
                }
                "Box office films of all time" -> {
                    b.mainFrame.isVisible = false
                    b.secMainText.apply {
                        isVisible = true
                        text = result.titleTxt
                    }
                }
                "My favourite movies" -> {
                    b.mainFrame.isVisible = false
                    b.secMainText.apply {
                        isVisible = true
                        text = result.titleTxt
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder =
        ItemHolder(
            MoviesFragmentItemsLyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    @SuppressLint("ResourceAsColor")
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val itemData = baseList[position]
        val top250MovieTitle = BaseUtils.getStringResource(
            holder.itemView.context, R.string.top_250_movies_title
        )
        val inTheatresTitle = BaseUtils.getStringResource(
            holder.itemView.context, R.string.in_theaters_title
        )
        val popMoviesTitle = BaseUtils.getStringResource(
            holder.itemView.context, R.string.most_pop_movies_title
        )
        val boxOffTitle = BaseUtils.getStringResource(
            holder.itemView.context, R.string.box_off_title
        )
        val favMoviesTitle = BaseUtils.getStringResource(
            holder.itemView.context, R.string.my_fav_movies_title
        )

        holder.bind(itemData)

        holder.b.mainBackgroundImage.setRenderEffect(
            RenderEffect.createBlurEffect(
                25f,
                25f,
                Shader.TileMode.MIRROR
            )
        )

        holder.b.secMainText.also {
            if (it.text == favMoviesTitle) {
                it.setTextColor(R.color.md_theme_light_onPrimaryContainer)
            }
        }

        holder.itemView.setOnClickListener {

            when (holder.b.secMainText.text) {
                top250MovieTitle -> {
                    val topMovieAction =
                        MoviesFragmentDirections.actionMoviesFragmentToTopMoviesFragment(
                            holder.b.secMainText.text.toString()
                        )
                    it.findNavController().navigate(topMovieAction)
                }
                favMoviesTitle -> {
                    val favMoviesAction =
                        MoviesFragmentDirections.actionMoviesFragmentToFavMoviesFragment()
                    it.findNavController().navigate(favMoviesAction)
                }

            }

            when (holder.b.categoryName.text) {
                inTheatresTitle -> {
                    val inTheatersAction =
                        MoviesFragmentDirections.actionMoviesFragmentToTopMoviesFragment(
                            holder.b.categoryName.text.toString()
                        )
                    it.findNavController().navigate(inTheatersAction)
                }

                popMoviesTitle -> {
                    val popMoviesAction =
                        MoviesFragmentDirections.actionMoviesFragmentToTopMoviesFragment(
                            holder.b.categoryName.text.toString()
                        )
                    it.findNavController().navigate(popMoviesAction)
                }

                boxOffTitle -> {
                    val boxOffAction =
                        MoviesFragmentDirections.actionMoviesFragmentToTopMoviesFragment(
                            holder.b.categoryName.text.toString()
                        )
                    it.findNavController().navigate(boxOffAction)
                }
            }

        }

    }

    override fun getItemCount(): Int = baseList.size

}