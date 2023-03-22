package com.jt17.neofilms.ui.helpers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.BottmSheetForFilmsBinding
import com.jt17.neofilms.models.ComingSoonModel
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.MostPopMoviesModel
import com.jt17.neofilms.models.MostPopTVShowsModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ModalBottomSheet(
    private val inTheatersData: InTheatresModel?,
    private val popMoviesData: MostPopMoviesModel?,
    private val popShowsData: MostPopTVShowsModel?
) : BottomSheetDialogFragment() {
    private var _binding: BottmSheetForFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = BottmSheetForFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scopingInTheatersData()

    }

    private fun scopingInTheatersData() = inTheatersData?.let {
        binding.BSHInThTitle.text = it.title
        binding.BSHImdbRatingRating.text = it.imDbRating
        binding.BSHGenres.text = it.genres
        binding.contentRating.text = it.contentRating
        binding.BSHTime.text = it.runtimeMins
        binding.BSHInThPlot.text = it.plot
        binding.BSHInThRealase.text = it.releaseState
        binding.BSHDirectorsIntH.text = it.directors

        Picasso.get().load(it.image).resize(110, 160).onlyScaleDown().error(R.color.black)
            .into(binding.BSHInTheatersImg)
    }

    private fun scopingPopMoviesData() = popMoviesData?.let {
        binding.BSHInThTitle.text = it.title
        binding.BSHImdbRatingRating.text = it.imDbRating
//        binding.BSHGenres.isVisible = false
//        binding.contentRating.text = it.
//        binding.BSHTime.text = it.runtimeMins
//        binding.BSHInThPlot.text = it.plot
//        binding.BSHInThRealase.text = it.releaseState
//        binding.BSHDirectorsIntH.text = it.directors

        Picasso.get().load(it.image).resize(110, 160).onlyScaleDown().error(R.color.black)
            .into(binding.BSHInTheatersImg)
    }

}