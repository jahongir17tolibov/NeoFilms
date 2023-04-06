package com.jt17.neofilms.ui.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentShowsBinding
import com.jt17.neofilms.models.MoviesModel
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.models.ShowsModel
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.ShowsFragmentAdapter
import com.jt17.neofilms.ui.adapters.TopShowsAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.ui.helpers.MarginItemDecoration
import com.jt17.neofilms.viewmodel.ShowsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ShowsFragment : Fragment(R.layout.fragment_shows) {
    private var _binding: FragmentShowsBinding? = null
    private lateinit var binding: FragmentShowsBinding

    private val viewModel by viewModels<ShowsViewModel>()
    private val showsFragmentAdapter by lazy { ShowsFragmentAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentShowsBinding.bind(view)
        _binding = binding

        initLiveData()
        initLoadData()
        setupRecyc()

    }

    private fun initLoadData() = viewModel.getMostPopShowsImg()

    private fun initLiveData() = viewModel.run {
        val showsList = mutableListOf<ShowsModel>()
        showsList.clear()

        mostPopShowsImageList.observe(viewLifecycleOwner) { imageList ->
            showsList.add(
                ShowsModel(
                    R.drawable.the_last_of_us_main,
                    imageList.subList(0, 4),
                    "TV shows that are popular now"
                )
            )
            showsFragmentAdapter.baseList = showsList.distinctBy { it.titleTxt }
        }

        showsList.also {
            it.add(
                ShowsModel(
                    R.drawable.top_shows_main,
                    null,
                    "Top 250 TV shows sorted by ImDb"
                )
            )
            showsFragmentAdapter.baseList = it.distinctBy { show -> show.titleTxt }
        }

        showsList.also {
            it.add(
                ShowsModel(
                    R.drawable.fav_shows_main,
                    null,
                    "My favourite TV shows"
                )
            )
            showsFragmentAdapter.baseList = it.distinctBy { show -> show.titleTxt }
        }

    }

    private fun setupRecyc() {
        val marginDecoration =
            MarginItemDecoration(resources.getDimensionPixelSize(R.dimen.last_item_margin))
        binding.mainRecyc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = showsFragmentAdapter
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