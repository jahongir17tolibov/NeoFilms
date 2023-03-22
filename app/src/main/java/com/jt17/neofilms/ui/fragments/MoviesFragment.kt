package com.jt17.neofilms.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentMoviesBinding
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.ui.adapters.TopMoviesAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private var _binding: FragmentMoviesBinding? = null
    private lateinit var binding: FragmentMoviesBinding

    private val viewModel by viewModels<MoviesViewModel>()
    private val topMoviesAdapter by lazy { TopMoviesAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)
        _binding = binding

        initLiveData()
        initLoadData()
        initRecyc()
    }

    private fun initLoadData() = viewModel.run {
        getTopMoviesData()
    }

    private fun initLiveData() = viewModel.run {
        topMoviesList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.moviesShimm.stopShimmer()
                    binding.moviesShimm.isVisible = false
                    binding.recycMovies.isVisible = true
                    resource.data?.let { topMoviesData ->
                        topMoviesAdapter.baselist = topMoviesData
                    }
                }

                Resource.Status.LOADING -> {
                    binding.moviesShimm.startShimmer()
                    binding.moviesShimm.isVisible = true
                    binding.recycMovies.isVisible = false
                }

                Resource.Status.ERROR -> {
                    binding.moviesShimm.stopShimmer()
                    binding.moviesShimm.isVisible = false
                    binding.recycMovies.isVisible = true
                    resource.data?.let {
                        topMoviesAdapter.baselist = it
                    }
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRecyc() {
        binding.recycMovies.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topMoviesAdapter

            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.moviesShimm.startShimmer()
    }

    override fun onPause() {
        binding.moviesShimm.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}