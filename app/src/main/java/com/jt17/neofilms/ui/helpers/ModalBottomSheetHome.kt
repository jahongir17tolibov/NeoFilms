package com.jt17.neofilms.ui.helpers

import android.annotation.SuppressLint
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.BottmSheetForFilmsBinding
import com.jt17.neofilms.models.*
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.TitleViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ModalBottomSheetHome(private val id: String?, private val state: Boolean?) :
    BottomSheetDialogFragment() {
    private var _binding: BottmSheetForFilmsBinding? = null
    private val binding get() = _binding!!
    override fun getTheme(): Int = R.style.BottomSheetBackgroundStyle

    private val viewModel by viewModels<TitleViewModel>()
    private var favMoviesModel: FavMoviesModel? = null
    private var favSeriesModel: FavSeriesModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = BottmSheetForFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLiveData()
        initLoadData()
        initClicks()

    }

    private fun initLoadData() = viewModel.run {
        getAllFilmsInfo(id!!)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initLiveData() = viewModel.apply {
        title.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { data ->
                        setData(data)
                    }
                    binding.progressCircular.isVisible = false
                    binding.alphaConstraintLl.isVisible = true
                    binding.addToFavBtn.isVisible = true
                }
                Resource.Status.LOADING -> {
                    binding.progressCircular.isVisible = true
                    binding.alphaConstraintLl.isVisible = false
                    binding.addToFavBtn.isVisible = false
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        resource.message,
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.progressCircular.isVisible = true
                    binding.alphaConstraintLl.isVisible = false
                    binding.addToFavBtn.isVisible = false
                }
            }
        }.launchIn(lifecycleScope)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private fun setData(data: TitleModel) = binding.apply {

        BSHInThTitle.text = data.title
        BSHImdbRatingRating.text = data.imDbRating
        BSHGenres.text = data.genres
        contentRating.text = data.contentRating
        BSHInThPlot.text = data.plot
        BSHInThRealase.text = BaseUtils.dateFormat(data.releaseDate)
        BSHStars.text = data.stars
        BSHmetacriticRating.text = data.metacriticRating
        BSHCompanies.text = data.companies
        BSHLanguages.text = data.languages

        if (data.awards.isNotBlank()) {
            BSHAwards.text = data.awards
        } else {
            binding.awardsLl.isVisible = false
        }

        if (data.runtimeStr != null) {
            BSHTime.text = data.runtimeStr
        } else {
            binding.durationsLl.isVisible = false
        }

        if (data.directors != null) {
            BSHDirectorsIntH.text = data.directors
        } else {
            binding.directorsLl.isVisible = false
        }

        Picasso.get().load(data.image)
            .resize(220, 320)
            .onlyScaleDown()
            .placeholder(R.drawable.movie_placeholder_img)
            .error(R.color.black)
            .into(binding.BSHInTheatersImg)


        binding.addToFavBtn.setOnClickListener {

            if (state == true) {
                viewModel.getFavSeriesID(data.id).observe(viewLifecycleOwner) { favSeries ->
                    favSeriesModel = favSeries
                    if (data.id != favSeriesModel?.seriesId.toString()) {
                        viewModel.insertFavSeries(
                            FavSeriesModel(
                                data.id,
                                data.title,
                                data.imDbRating,
                                data.year,
                                data.image,
                                data.stars,
                                data.hashCode()
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "TV show successfully added to favorites!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "This TV show is available in favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                viewModel.getFavMovieID(data.id).observe(viewLifecycleOwner) { favMovie ->
                    favMoviesModel = favMovie
                    if (data.id != favMoviesModel?.movieId.toString()) {
                        viewModel.insertFavMovie(
                            FavMoviesModel(
                                data.id,
                                data.title,
                                data.imDbRating,
                                data.year,
                                data.image,
                                data.stars,
                                data.hashCode()
                            )
                        )
                        Toast.makeText(
                            requireContext(),
                            "Movie successfully added to favorites!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "This movie is available in favorites",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

    }

    private fun initClicks() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val TAG = "ModalBottomSheet"
    }

}