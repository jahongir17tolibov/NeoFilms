package com.jt17.neofilms.ui.screens

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentMoviesBinding
import com.jt17.neofilms.models.MoviesModel
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.MoviesFragmentAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.ui.helpers.MarginItemDecoration
import com.jt17.neofilms.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private var _binding: FragmentMoviesBinding? = null
    private lateinit var binding: FragmentMoviesBinding

    private val viewModel by activityViewModels<MoviesViewModel>()
    private val moviesFragmentAdapter by lazy { MoviesFragmentAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesBinding.bind(view)
        _binding = binding

        initLoadData()
        initLiveData()
        setupRecyc()

    }

    private fun initLoadData() = viewModel.run {
        getInTheatersImg()
        getMostPopMoviesImg()
        getBoxOfficeImg()
    }

    private fun initLiveData() = viewModel.run {
        val moviesList = mutableListOf<MoviesModel>()
        moviesList.clear()

        inTheatersImageList.observe(viewLifecycleOwner) { imageList ->
            moviesList.add(
                MoviesModel(
                    R.drawable.in_theaters,
                    imageList.subList(0, 4),
                    "Movies currently playing in theaters"
                )
            )
            moviesFragmentAdapter.baseList = moviesList.distinctBy { it.titleTxt }
        }

        mostPopMoviesImageList.observe(viewLifecycleOwner) { mostPopList ->

            moviesList.add(
                MoviesModel(
                    R.drawable.john_wick_4,
                    mostPopList.subList(0, 4).shuffled(),
                    "Movies that are popular now"
                )
            )
            moviesFragmentAdapter.baseList = moviesList.distinctBy { it.titleTxt }
        }

        boxOfficeImageList.observe(viewLifecycleOwner) { boxOfficeList ->

            moviesList.add(
                MoviesModel(
                    R.drawable.box_off_main,
                    boxOfficeList.subList(0, 4),
                    "Current top movies box office"
                )
            )
            moviesFragmentAdapter.baseList = moviesList.distinctBy { it.titleTxt }
        }

        moviesList.also {
            it.add(
                MoviesModel(
                    R.drawable.top_movies_main,
                    null,
                    "Top 250 movies sorted by ImDb"
                )
            )
            moviesFragmentAdapter.baseList = it.distinctBy { dis -> dis.titleTxt }
        }

        moviesList.also {
            it.add(
                MoviesModel(
                    R.drawable.my_favourite_films_main,
                    null,
                    "My favourite movies"
                )
            )
            moviesFragmentAdapter.baseList = it.distinctBy { dis -> dis.titleTxt }
        }

    }

    private fun setupRecyc() {
        val marginDecoration =
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.last_item_margin))

        binding.mainRecyc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = moviesFragmentAdapter
            edgeEffectFactory = BounceEdgeEffectFactory()
            addItemDecoration(marginDecoration)
        }

    }

    override fun onResume() {
        super.onResume()
        val mainActivity = activity as MainActivity
        val bottomNav = mainActivity.findViewById<BottomNavigationView>(R.id.bottom_navigate)
        bottomNav.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}