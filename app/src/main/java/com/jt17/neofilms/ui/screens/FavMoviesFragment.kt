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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentFavMoviesBinding
import com.jt17.neofilms.models.FavMoviesModel
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.FavMoviesAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavMoviesFragment : Fragment(R.layout.fragment_fav_movies) {
    private var _binding: FragmentFavMoviesBinding? = null
    private lateinit var binding: FragmentFavMoviesBinding

    private val viewModel by viewModels<MoviesViewModel>()
    private val favMoviesAdapter by lazy { FavMoviesAdapter() }
    private lateinit var dialog: MaterialAlertDialogBuilder
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavMoviesBinding.bind(view)
        _binding = binding

        binding.toolbar.title = "My favourite movies"

        initLiveData()
        setupRecycler()
        initClicks()

    }

    private fun initLiveData() = viewModel.run {
        getAllFavMovies.observe(viewLifecycleOwner) {
            favMoviesAdapter.submitList(it)
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
        adapter = favMoviesAdapter
        edgeEffectFactory = BounceEdgeEffectFactory()
    }

    private fun initClicks() {

        binding.clearAllBtn.setOnClickListener {
            initClearAllDialog()
        }

        favMoviesAdapter.setOnLongItemClickListener {
            initDeleteOneDialog(it)
        }

        binding.toolbar.setNavigationOnClickListener {
            val action = FavMoviesFragmentDirections.actionFavMoviesFragmentToMoviesFragment()
            findNavController().navigate(action)
        }

    }

    private fun initClearAllDialog() {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete all your favorite movies?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Clear all") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your all favorite movies deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                viewModel.clearAllFavMovies()
            }
        dialog.show()
    }

    private fun initDeleteOneDialog(favMoviesModel: FavMoviesModel) {
        dialog = MaterialAlertDialogBuilder(requireContext(), R.style.MyCustomizedDialog)
            .setTitle("Are you sure you want to delete this favorite movie?")
            .setNeutralButton("Cancel") { dialog, _ ->
                dialog.cancel()
            }
            .setPositiveButton("Delete") { dialog, _ ->
                Toast.makeText(
                    requireContext(),
                    "Your favourite movie deleted",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
                viewModel.deleteOneFavMovie(favMoviesModel)
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