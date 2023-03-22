package com.jt17.neofilms.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentHomeBinding
import com.jt17.neofilms.models.*
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.InTheatersAdapter
import com.jt17.neofilms.ui.adapters.MostPopMoviesAdapter
import com.jt17.neofilms.ui.adapters.MostPopShowsAdapter
import com.jt17.neofilms.ui.adapters.SliderAdapter
import com.jt17.neofilms.ui.helpers.ModalBottomSheet
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.HomeViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    private val comingSoonAdapter by lazy { SliderAdapter() }
    private val inTheatersAdapter by lazy { InTheatersAdapter() }
    private val mostPopularMoviesAdapter by lazy { MostPopMoviesAdapter() }
    private val mostPopShowsAdapter by lazy { MostPopShowsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding

        initRecyc()
        initLivedata()
        initLoadData()

        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }
        swipeRefresh()

    }

    private fun initLoadData() {
        viewModel.run {
            getComingSoonData()
            getInTheatersData()
            getMostPopMoviesData()
            getMostPopShowsData()
        }
    }

    private fun initLivedata() = viewModel.run {

        comingSoonList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { data ->
                        comingSoonAdapter.comingSoonBaseList = data.toMutableList().shuffled()
                    }
                    binding.swipeContainer.isRefreshing = false

                }

                Resource.Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }

                Resource.Status.ERROR -> {
                    resource.data?.let {
                        comingSoonAdapter.comingSoonBaseList = it.toMutableList().shuffled()
                    }
                    binding.swipeContainer.isRefreshing = false
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.launchIn(lifecycleScope)

        inTheatersList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { inTheatersData ->
                        inTheatersAdapter.baseList = inTheatersData
                        binding.swipeContainer.isRefreshing = false
                    }
                }

                Resource.Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }

                Resource.Status.ERROR -> {
                    resource.data?.let {
                        inTheatersAdapter.baseList = it
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }
        }.launchIn(lifecycleScope)

        mostPopMoviesList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { popMoviesData ->
                        mostPopularMoviesAdapter.baseList = popMoviesData
                        binding.swipeContainer.isRefreshing = false
                    }
                }

                Resource.Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }

                Resource.Status.ERROR -> {
                    resource.data?.let {
                        mostPopularMoviesAdapter.baseList = it
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }
        }.launchIn(lifecycleScope)

        mostPopShowsList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { popShowsData ->
                        mostPopShowsAdapter.baseList = popShowsData
                        binding.swipeContainer.isRefreshing = false
                    }
                }

                Resource.Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }

                Resource.Status.ERROR -> {
                    resource.data?.let {
                        mostPopShowsAdapter.baseList = it
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }
        }.launchIn(lifecycleScope)

    }

    private fun initRecyc() {
        binding.inTheatersRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = inTheatersAdapter
        }

        binding.popularMoviesRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularMoviesAdapter
        }

        binding.popularShowsRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopShowsAdapter
            setHasFixedSize(true)
        }

        binding.comingSoonSlider.apply {
            setSliderAdapter(comingSoonAdapter)
            setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
            setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
            scrollTimeInSec = 3
            autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
            startAutoCycle()
        }

    }

    private fun initClicks() {
        inTheatersAdapter.setOnItemClickListener { inTheatersData ->
            val modalBottomSheet = ModalBottomSheet(inTheatersData, null, null)
            modalBottomSheet.show(
                requireActivity().supportFragmentManager,
                ModalBottomSheet.TAG
            )
        }

        mostPopularMoviesAdapter.setOnItemClickListener { popMosviesData ->

//            val modalBottomSheet = ModalBottomSheet()
//            modalBottomSheet.show(
//                requireActivity().supportFragmentManager,
//                ModalBottomSheet.TAG
//            )
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun swipeRefresh() {
        binding.swipeContainer.setColorSchemeResources(
            R.color.purple_200,
            R.color.purple_500,
            R.color.purple_700
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = true
    }


}
