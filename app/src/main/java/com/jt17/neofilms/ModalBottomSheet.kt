package com.jt17.neofilms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jt17.neofilms.databinding.BottmSheetForFilmsBinding
import com.jt17.neofilms.models.InTheatresModel
import com.squareup.picasso.Picasso

class ModalBottomSheet(val itemData: InTheatresModel) : BottomSheetDialogFragment() {
    private var _binding: BottmSheetForFilmsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BottmSheetForFilmsBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        itemData.also {
            binding.BSHInThTitle.text = it.title
            binding.BSHImdbRatingRating.text = it.imDbRating
            binding.BSHGenres.text = it.genres
            binding.contentRating.text = it.contentRating
            binding.BSHTime.text = it.runtimeMins
            binding.BSHInThPlot.text = it.plot
            binding.BSHInThRealase.text = it.releaseState
            binding.BSHDirectorsIntH.text = it.directors

            Picasso.get().load(it.image).error(R.color.black).into(binding.BSHInTheatersImg)
        }
    }
}