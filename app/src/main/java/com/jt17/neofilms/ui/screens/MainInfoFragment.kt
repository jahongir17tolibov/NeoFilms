package com.jt17.neofilms.ui.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jt17.neofilms.R
import com.jt17.neofilms.databinding.FragmentMainInfoBinding
import com.jt17.neofilms.models.Resource
import com.jt17.neofilms.models.TitleModel
import com.jt17.neofilms.ui.activities.MainActivity
import com.jt17.neofilms.ui.adapters.CastAdapter
import com.jt17.neofilms.ui.helpers.BounceEdgeEffectFactory
import com.jt17.neofilms.utils.BaseUtils
import com.jt17.neofilms.viewmodel.MoviesViewModel
import com.jt17.neofilms.viewmodel.TitleViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@AndroidEntryPoint
class MainInfoFragment : Fragment(R.layout.fragment_main_info) {
    private var _binding: FragmentMainInfoBinding? = null
    private lateinit var binding: FragmentMainInfoBinding

    private val viewModel by viewModels<TitleViewModel>()
    private val args by navArgs<MainInfoFragmentArgs>()
    private val castAdapter by lazy { CastAdapter() }
    private val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainInfoBinding.bind(view)
        _binding = binding

        binding.episodesGuide.isVisible = args.epsState

        initLiveData()
        initLoadData()
        setupRecycler()
        initClicks()

    }

    private fun initLoadData() = viewModel.run {
        getAllFilmsInfo(args.moviesId!!)
        getFilmsWiki(args.moviesId!!)
    }

    private fun initLiveData() = viewModel.run {
        title.onEach { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { titleData ->
                        setData(titleData)
                        castAdapter.baseList = titleData.actorList!!
                    }
                    binding.apply {
                        progressIndicator.isVisible = false
                        appBarLy.isVisible = true
                        nestedScrollView.isVisible = true
                    }
                }

                Resource.Status.LOADING -> binding.apply {
                    progressIndicator.isVisible = true
                    appBarLy.isVisible = false
                    nestedScrollView.isVisible = false
                }


                Resource.Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Check your internet connection!\n${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.apply {
                        progressIndicator.isVisible = true
                        appBarLy.isVisible = false
                        nestedScrollView.isVisible = false
                    }
                }

            }
        }.launchIn(lifecycleScope)

        wikiData.observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let { titleData ->
                        titleData.plotShort?.let {
                            binding.aboutAFilm.text = it.plainText
                        }
                    }
                    binding.apply {
                        progressIndicator.isVisible = false
                        appBarLy.isVisible = true
                        nestedScrollView.isVisible = true
                    }
                }

                Resource.Status.LOADING -> binding.apply {
                    progressIndicator.isVisible = true
                    appBarLy.isVisible = false
                    nestedScrollView.isVisible = false
                }


                Resource.Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        "Check your internet connection!\n${resource.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.apply {
                        progressIndicator.isVisible = true
                        appBarLy.isVisible = false
                        nestedScrollView.isVisible = false
                    }
                }

            }
        }
    }


    private fun setData(data: TitleModel) = binding.apply {
        binding.toolbar.title = data.title

        imdbRatingMovies.text = data.imDbRating
        metaScore.text = data.metacriticRating
        duration.text = data.runtimeStr
        contentRating.text = data.contentRating
        companies.text = data.companies
        releaseDate.text = BaseUtils.dateFormat(data.releaseDate)
        directors.text = data.directors
        writers.text = data.writers
        languages.text = data.languages
        countries.text = data.countries
        budget.text = data.boxOffice?.budget
        openingWeekend.text = data.boxOffice?.openingWeekendUSA
        grossHome.text = data.boxOffice?.grossUSA
        worldGross.text = data.boxOffice?.cumulativeWorldwideGross
        awards.text = data.awards
        genres.text = data.genres

        Picasso.get().load(data.image)
            .placeholder(R.drawable.movie_placeholder_img)
            .resize(140, 200)
            .onlyScaleDown()
            .error(R.color.black)
            .into(binding.mainIMG)

        if (binding.episodesGuide.isVisible) {
            binding.durTxt.text = BaseUtils.getStringResource(requireContext(), R.string.season_txt)
            binding.duration.text = data.seasons?.last()
            Log.d("jt1771tj", "setData: ${data.seasons}")
            data.tvSeriesInfo?.let {
                if (it.yearEnd.isEmpty()) {
                    binding.status.text = "active"
                } else {
                    binding.status.text = "last eps in ${it.yearEnd}"
                }
            }
            binding.creators.text = data.tvSeriesInfo?.creators
            binding.apply {
                dirTxt.isVisible = false
                wrtTxt.isVisible = false
                budgetLl.isVisible = false
                openingLl.isVisible = false
                worlsLl.isVisible = false
                homeLl.isVisible = false
                creTxt.isVisible = true
                statTxt.isVisible = true
            }
        }

    }

    private fun setupRecycler() {
        binding.castRecyc.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }

        binding.aboutAFilm.also { text ->
            val originalMaxLines = text.maxLines
            text.setOnClickListener {
                if (text.maxLines == originalMaxLines) {
                    text.maxLines = Int.MAX_VALUE
                } else {
                    text.maxLines = originalMaxLines
                }
                text.requestLayout()
            }
        }
    }

    private fun initClicks() {

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