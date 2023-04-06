package com.jt17.neofilms.ui.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.data.sharedPreferences.AppPreferences
import com.jt17.neofilms.databinding.FragmentHomeBinding
import com.jt17.neofilms.models.*
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.*
import com.jt17.neofilms.ui.helpers.ModalBottomSheetHome
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.HomeViewModel
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
    private val boxOfficeAdapter by lazy { BoxOfficeAdapter() }
    private val navController by lazy { findNavController() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding

        initLivedata()
        initLoadData()
        setupRecycler()

        initClicks()

        swipeRefresh()
        binding.swipeContainer.setOnRefreshListener {
            initLoadData()
        }

    }

    private fun initLoadData() = viewModel.run {
        getComingSoonData()
        getInTheatersData()
        getMostPopMoviesData()
        getMostPopShowsData()
        getBoxOfficeMoviesData()
    }

    private fun initLivedata() = viewModel.run {

        comingSoonList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { data ->
                        comingSoonAdapter.comingSoonBaseList = data.shuffled()
                    }

                    binding.comingSoonSlider.apply {
                        setSliderAdapter(comingSoonAdapter)
                        setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
                        scrollTimeInSec = 5
                        autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
                        startAutoCycle()
                    }
                    shimmerIsStopped()/* stopping to shimmer loading effect */

                }

                Resource.Status.LOADING -> shimmerIsStarted()

                Resource.Status.ERROR -> {
                    resource.data?.let {
                        comingSoonAdapter.comingSoonBaseList = it.toMutableList().shuffled()
                    }

                    binding.comingSoonSlider.apply {
                        setSliderAdapter(comingSoonAdapter)
                        setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
                        scrollTimeInSec = 4
                        autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
                        startAutoCycle()
                    }

                    shimmerIsStopped()
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
                    resource.data?.let { data ->
                        mostPopShowsAdapter.baseList = data
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }
        }.launchIn(lifecycleScope)

        boxOffList.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { popShowsData ->
                        boxOfficeAdapter.baseList = popShowsData
                        binding.swipeContainer.isRefreshing = false
                    }
                }

                Resource.Status.LOADING -> {
                    binding.swipeContainer.isRefreshing = true
                }

                Resource.Status.ERROR -> {
                    resource.data?.let { data ->
                        boxOfficeAdapter.baseList = data
                    }
                    binding.swipeContainer.isRefreshing = false
                }
            }
        }.launchIn(lifecycleScope)

    }

    private fun setupRecycler() = binding.run {
        inTheatersRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = inTheatersAdapter
            setHasFixedSize(true)
        }

        popularMoviesRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopularMoviesAdapter
            setHasFixedSize(true)
        }

        popularShowsRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = mostPopShowsAdapter
            setHasFixedSize(true)
        }

        boxOffRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = boxOfficeAdapter
            setHasFixedSize(true)
        }
    }

    private fun changeTheme() {
        val pos = when (!AppPreferences.getInstance().loadNightModeState()) {
            true -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            false -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }
        AppPreferences.getInstance().setNightModeState(pos)
    }

    private fun initClicks() {
        inTheatersAdapter.setOnItemClickListener { inTheatersData ->

            val modalBottomSheetHome = ModalBottomSheetHome(inTheatersData.id, false)
            modalBottomSheetHome.show(
                requireActivity().supportFragmentManager, ModalBottomSheetHome.TAG
            )
        }

        mostPopularMoviesAdapter.setOnItemClickListener { popMoviesData ->

            val modalBottomSheetHome = ModalBottomSheetHome(popMoviesData.id, false)
            modalBottomSheetHome.show(
                requireActivity().supportFragmentManager, ModalBottomSheetHome.TAG
            )
        }

        mostPopShowsAdapter.setOnItemClickListener { popShowsData ->

            val modalBottomSheetHome = ModalBottomSheetHome(popShowsData.id, true)
            modalBottomSheetHome.show(
                requireActivity().supportFragmentManager, ModalBottomSheetHome.TAG
            )
        }

        boxOfficeAdapter.setOnItemClickListener { boxOfficeData ->

            val modalBottomSheetHome = ModalBottomSheetHome(boxOfficeData.id, false)
            modalBottomSheetHome.show(
                requireActivity().supportFragmentManager, ModalBottomSheetHome.TAG
            )
        }
        binding.inTheatersMoreBtn.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToTopMoviesFragment(
                    BaseUtils.getStringResource(
                        requireContext(),
                        R.string.in_theaters_title
                    )
                )
            navController.navigate(action)
        }

        binding.popMoviesMoreBtn.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToTopMoviesFragment(
                    BaseUtils.getStringResource(
                        requireContext(),
                        R.string.most_pop_movies_title
                    )
                )
            navController.navigate(action)
        }

        binding.boxOffMoreBtn.setOnClickListener {
            val action =
                HomeFragmentDirections.actionHomeFragmentToTopMoviesFragment(
                    BaseUtils.getStringResource(
                        requireContext(),
                        R.string.box_off_title
                    )
                )
            navController.navigate(action)
        }

        binding.popTvShowsMoreBtn.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToTopShowsFragment(
                BaseUtils.getStringResource(requireContext(), R.string.most_pop_shows_title)
            )
            navController.navigate(action)
        }

        binding.lightDarkButton.setOnClickListener {
            val pref = AppPreferences.getInstance()
            pref.loadNightModeState() != pref.loadNightModeState()
            changeTheme()
            requireActivity().recreate()
        }
    }

    private fun shimmerIsStopped() = binding.apply {
        shimmer.stopShimmer()
        shimmer.isVisible = false
        appBarLy.isVisible = true
        nestedScrollView.isVisible = true
        swipeContainer.isRefreshing = false
    }

    private fun shimmerIsStarted() = binding.apply {
        shimmer.startShimmer()
        swipeContainer.isRefreshing = true
        shimmer.isVisible = true
        appBarLy.isVisible = false
        nestedScrollView.isVisible = false
    }

    @SuppressLint("ResourceAsColor")
    private fun swipeRefresh() = binding.swipeContainer.setColorSchemeResources(
        R.color.purple_200, R.color.purple_500, R.color.purple_700
    )

    override fun onDestroyView() {
        super.onDestroyView()
        fragmentHomeBinding = null
    }

    override fun onResume() {
        super.onResume()
        binding.shimmer.startShimmer()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = true
    }

    override fun onPause() {
        binding.shimmer.stopShimmer()
        super.onPause()
    }

}
