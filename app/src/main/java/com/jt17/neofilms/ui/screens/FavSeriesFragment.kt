package com.jt17.neofilms.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentFavSeriesBinding
import com.jt17.neofilms.models.FavMoviesModel
import com.jt17.neofilms.models.FavSeriesModel
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.FavMoviesAdapter
import com.jt17.neofilms.ui.adapters.FavSeriesAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.viewmodel.MoviesViewModel
import com.jt17.neofilms.viewmodel.ShowsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavSeriesFragment : Fragment(R.layout.fragment_fav_series) {
    private var _binding: FragmentFavSeriesBinding? = null
    private lateinit var binding: FragmentFavSeriesBinding

    private val viewModel by viewModels<ShowsViewModel>()
    private val favSeriesAdapter by lazy { FavSeriesAdapter() }
    private lateinit var dialog: MaterialAlertDialogBuilder
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavSeriesBinding.bind(view)
        _binding = binding

        binding.toolbar.title = "My favourite TV shows"

        initLiveData()
        setupRecycler()
        initClicks()

    }

    private fun initLiveData() = viewModel.run {
        getAllFavSeries.observe(viewLifecycleOwner) {
            favSeriesAdapter.submitList(it)
            if (it.isNotEmpty()) {
                binding.favRecyc.isVisible = true
                binding.favTxt.isVisible = false
            } else {
                binding.favRecyc.isVisible = false
                binding.favTxt.isVisible = true
            }
        }
    }

    private fun setupRecycler() = binding.favRecyc.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favSeriesAdapter
        edgeEffectFactory = BounceEdgeEffectFactory()
    }

    private fun initClicks() {

        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()
        }

        favSeriesAdapter.setOnLongItemClickListener {
            initDeleteOneDialog(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = FavSeriesFragmentDirections.actionFavSeriesFragmentToShowsFragment()
            findNavController().navigate(action)
        }

    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite TV shows?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your all favorite TV shows deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                viewModel.clearAllFavSeries()
            }
        dialog.show()
    }

    private fun initDeleteOneDialog(favSeriesModel: FavSeriesModel) {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete this favorite TV show?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Delete") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your favourite TV show deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                viewModel.deleteOneFavSeries(favSeriesModel)
            }
        dialog.show()
    }

    override fun onResume() {
        super.onResume()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}