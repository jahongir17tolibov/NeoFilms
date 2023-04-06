package com.jt17.neofilms.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentTopMoviesBinding
import com.jt17.neofilms.models.FavMoviesModel
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.MostPopMoviesModel
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.*
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopMoviesFragment : Fragment(R.layout.fragment_top_movies) {
    private var _binding: FragmentTopMoviesBinding? = null
    private lateinit var binding: FragmentTopMoviesBinding
    private val args by navArgs<TopMoviesFragmentArgs>()

    private val viewModel by viewModels<MoviesViewModel>()
    private val topMoviesAdapter by lazy { TopMoviesAdapter() }
    private val boxOfficeAdapter by lazy { BoxOfficeAdapter() }
    private val cachedInTheatersAdapter by lazy { CachedInTheatersAdapter() }
    private val cachedMostPopMoviesAdapter by lazy { CachedMostPopMoviesAdapter() }
    private var favMoviesModel: FavMoviesModel? = null
    private lateinit var dialog: MaterialAlertDialogBuilder
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopMoviesBinding.bind(view)
        _binding = binding

        binding.toolbar.title = args.actionBarName

        initLiveData()
        initLoadData()
        setupRecycler()
        initClicks()

    }

    private fun initLoadData() {
        if (binding.toolbar.title == BaseUtils.getStringResource(
                requireContext(),
                R.string.top_250_movies_title
            )
        ) {
            viewModel.getTopMoviesData()
        }
    }

    private fun initLiveData() {
        when (binding.toolbar.title) {
            BaseUtils.getStringResource(requireContext(), R.string.top_250_movies_title) -> {
                viewModel.topMoviesList.onEach { resource ->
                    when (resource.status) {
                        Resource.Status.SUCCESS -> {
                            binding.apply {
                                moviesShimm.stopShimmer()
                                moviesShimm.isVisible = false
                                appBarLy.isVisible = true
                                recycMovies.isVisible = true
                            }

                            resource.data?.let { topMoviesData ->
                                topMoviesAdapter.baselist = topMoviesData
                            }
                        }

                        Resource.Status.LOADING -> {
                            binding.apply {
                                moviesShimm.startShimmer()
                                moviesShimm.isVisible = true
                                appBarLy.isVisible = false
                                recycMovies.isVisible = false
                            }

                        }

                        Resource.Status.ERROR -> {
                            binding.apply {
                                moviesShimm.stopShimmer()
                                moviesShimm.isVisible = false
                                appBarLy.isVisible = true
                                recycMovies.isVisible = true
                            }
                            resource.data?.let {
                                topMoviesAdapter.baselist = it
                            }
                            Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }.launchIn(lifecycleScope)
            }

            BaseUtils.getStringResource(requireContext(), R.string.box_off_title) -> {
                viewModel.getAllBoxOfficeData.observe(viewLifecycleOwner) {
                    boxOfficeAdapter.baseList = it
                    binding.apply {
                        moviesShimm.stopShimmer()
                        moviesShimm.isVisible = false
                        appBarLy.isVisible = true
                        recycMovies.isVisible = true
                    }
                }
            }

            BaseUtils.getStringResource(requireContext(), R.string.most_pop_movies_title) -> {
                viewModel.getAllMostPopMoviesData.observe(viewLifecycleOwner) {
                    cachedMostPopMoviesAdapter.baselist = it
                    binding.apply {
                        moviesShimm.stopShimmer()
                        moviesShimm.isVisible = false
                        appBarLy.isVisible = true
                        recycMovies.isVisible = true
                    }
                }
            }

            BaseUtils.getStringResource(requireContext(), R.string.in_theaters_title) -> {
                viewModel.getAllInTheatresData.observe(viewLifecycleOwner) {
                    cachedInTheatersAdapter.baselist = it
                    binding.apply {
                        moviesShimm.stopShimmer()
                        moviesShimm.isVisible = false
                        appBarLy.isVisible = true
                        recycMovies.isVisible = true
                    }
                }
            }
        }
    }

    private fun setupRecycler() = binding.recycMovies.apply {
        if (binding.toolbar.title == BaseUtils.getStringResource(
                requireContext(),
                R.string.box_off_title
            )
        ) {
            layoutManager = GridLayoutManager(requireContext(), 3)
            adapter = boxOfficeAdapter
            edgeEffectFactory = BounceEdgeEffectFactory()
        } else {
            layoutManager = LinearLayoutManager(requireContext())
            when (binding.toolbar.title) {
                BaseUtils.getStringResource(
                    requireContext(),
                    R.string.top_250_movies_title
                ) -> adapter = topMoviesAdapter
                BaseUtils.getStringResource(
                    requireContext(),
                    R.string.in_theaters_title
                ) -> adapter = cachedInTheatersAdapter
                BaseUtils.getStringResource(
                    requireContext(),
                    R.string.most_pop_movies_title
                ) -> adapter = cachedMostPopMoviesAdapter
            }
            edgeEffectFactory = BounceEdgeEffectFactory()
        }
    }

    private fun initClicks() {

        binding.toolbar.setNavigationOnClickListener {
            //back to movies fragment and remove these fragment on the stack
            val moviesAction = TopMoviesFragmentDirections.actionTopMoviesFragmentToMoviesFragment()
            it.findNavController().navigate(moviesAction)
        }

        boxOfficeAdapter.setOnItemClickListener {
            val action =
                TopMoviesFragmentDirections.actionTopMoviesFragmentToMainInfoFragment(it.id, false)
            findNavController().navigate(action)
        }

        topMoviesAdapter.setOnFavItemClickListener {

            viewModel.getFavMovieID(it.id).observe(viewLifecycleOwner) { favMovie ->
                favMoviesModel = favMovie
                if (it.id != favMoviesModel?.movieId.toString()) {
                    viewModel.insertFavMovie(
                        FavMoviesModel(
                            it.id,
                            it.title,
                            it.imDbRating,
                            it.year,
                            it.image,
                            it.crew,
                            it.topMoviesID
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

        cachedMostPopMoviesAdapter.setOnFavItemClickListener { mostPopMoviesModel ->
            viewModel.getFavMovieID(mostPopMoviesModel.id)
                .observe(viewLifecycleOwner) { favMovie ->
                    favMoviesModel = favMovie
                    if (mostPopMoviesModel.id != favMoviesModel?.movieId.toString()) {
                        viewModel.insertFavMovie(
                            FavMoviesModel(
                                mostPopMoviesModel.id,
                                mostPopMoviesModel.title,
                                mostPopMoviesModel.imDbRating,
                                mostPopMoviesModel.year,
                                mostPopMoviesModel.image,
                                mostPopMoviesModel.crew,
                                mostPopMoviesModel.mostPopMoviesID
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

        cachedInTheatersAdapter.setOnFavItemClickListener { inTheatresModel ->
            viewModel.getFavMovieID(inTheatresModel.id)
                .observe(viewLifecycleOwner) { favMovie ->
                    favMoviesModel = favMovie
                    if (inTheatresModel.id != favMoviesModel?.movieId!!.toString()) {
                        viewModel.insertFavMovie(
                            FavMoviesModel(
                                inTheatresModel.id,
                                inTheatresModel.title,
                                inTheatresModel.imDbRating,
                                inTheatresModel.year,
                                inTheatresModel.image,
                                inTheatresModel.plot,
                                inTheatresModel.inThID
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

    override fun onResume() {
        super.onResume()
        binding.moviesShimm.startShimmer()

        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = false
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

