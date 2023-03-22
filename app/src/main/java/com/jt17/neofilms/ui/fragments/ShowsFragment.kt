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
import com.jt17.neofilms.databinding.FragmentShowsBinding
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.ui.adapters.TopShowsAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.ShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.fragment_shows) {
    private var _binding: FragmentShowsBinding? = null
    private lateinit var binding: FragmentShowsBinding

    private val viewModel by viewModels<ShowsViewModel>()
    private val topShowsAdapter by lazy { TopShowsAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowsBinding.bind(view)
        _binding = binding

        initLiveData()
        initLoadData()
        initRecyc()

    }

    private fun initLoadData() = viewModel.run {
        getTopShowsData()
        binding.showsShimm.stopShimmer()
    }

    private fun initLiveData() = viewModel.run {
        topShowsList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    binding.showsShimm.stopShimmer()
                    binding.showsShimm.isVisible = false
                    binding.recycShows.isVisible = true
                    resource.data?.let { topMoviesData ->
                        topShowsAdapter.topShowsList = topMoviesData
                    }
                }

                Resource.Status.LOADING -> {
                    binding.showsShimm.startShimmer()
                    binding.showsShimm.isVisible = true
                    binding.recycShows.isVisible = false
                }

                Resource.Status.ERROR -> {
                    binding.showsShimm.stopShimmer()
                    binding.showsShimm.isVisible = false
                    binding.recycShows.isVisible = true
                    resource.data?.let {
                        topShowsAdapter.topShowsList = it
                    }
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)
    }

    private fun initRecyc() {
        binding.recycShows.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topShowsAdapter

            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.showsShimm.startShimmer()
    }

    override fun onPause() {
        binding.showsShimm.stopShimmer()
        super.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}