package com.jt17.neofilms.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentTopShowsBinding
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.CachedMostPopShowsAdapter
import com.jt17.neofilms.ui.adapters.TopShowsAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.ShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class TopShowsFragment : Fragment(R.layout.fragment_top_shows) {
    private var _binding: FragmentTopShowsBinding? = null
    private lateinit var binding: FragmentTopShowsBinding

    private val viewModel by viewModels<ShowsViewModel>()
    private val args by navArgs<TopShowsFragmentArgs>()
    private val topShowsAdapter by lazy { TopShowsAdapter() }
    private val cachedMostPopShowsAdapter by lazy { CachedMostPopShowsAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopShowsBinding.bind(view)
        _binding = binding

        binding.toolbar.title = args.actionBarTitle

        initLiveData()
        initLoadData()
        setupRecycler()
        initClicks()

    }

    private fun initLoadData() = viewModel.run {
        if (binding.toolbar.title == BaseUtils.getStringResource(
                requireContext(), R.string.top_250_shows_title
            )
        ) getTopShowsData()
    }

    private fun initLiveData() {
        when (binding.toolbar.title) {
            BaseUtils.getStringResource(
                requireContext(),
                R.string.top_250_shows_title
            ) -> {
                viewModel.topShowsList.onEach { resource ->
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
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }.launchIn(lifecycleScope)
            }
            BaseUtils.getStringResource(
                requireContext(),
                R.string.most_pop_shows_title
            ) -> {
                viewModel.getAllMostPopShowsCachedList.observe(viewLifecycleOwner) {
                    cachedMostPopShowsAdapter.baselist = it
                    binding.showsShimm.stopShimmer()
                    binding.showsShimm.isVisible = false
                    binding.recycShows.isVisible = true
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.recycShows.run {
            layoutManager = LinearLayoutManager(requireContext())
            when (binding.toolbar.title) {
                BaseUtils.getStringResource(
                    requireContext(),
                    R.string.top_250_shows_title
                ) -> adapter = topShowsAdapter
                BaseUtils.getStringResource(
                    requireContext(),
                    R.string.most_pop_shows_title
                ) -> adapter = cachedMostPopShowsAdapter
            }
            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    private fun initClicks() {
        binding.toolbar.setNavigationOnClickListener {
            val action = TopShowsFragmentDirections.actionTopShowsFragmentToShowsFragment()
            it.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.showsShimm.startShimmer()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = false
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