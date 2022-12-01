package com.jt17.neofilms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.MainNestedAdapter
import com.jt17.neofilms.adapters.SliderMPM_Adapter
import com.jt17.neofilms.databinding.FragmentHomeBinding
import com.jt17.neofilms.models.NestedModel
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.jt17.neofilms.viewmodel.BaseViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_home : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: BaseViewModel
    private lateinit var mylist: ArrayList<NestedModel>
    private val key = "k_jo0bfe4d"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BaseViewModel::class.java]

        binding.swipeContainer.isRefreshing = true

        binding.swipeContainer.setOnRefreshListener {
            loadApi()
            nestedApi()
        }
        initLivedata()

//nested recyclerview in theatres
        binding.apply {
            nestedRecycHome.layoutManager = LinearLayoutManager(requireContext())
        }

    }

    private fun loadApi() {

        NetManager.getApiService().getApi_topM("MostPopularMovies", key)
            .enqueue(object : Callback<imdbApiModel<List<mainModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<mainModel>>>,
                    response: Response<imdbApiModel<List<mainModel>>>
                ) {
                    if (response.isSuccessful) {
                        viewModel.netList.value = response.body()!!.items
//                        viewModel.nestedList.value = mylist
                    }
                }

                override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Check internet connection ;)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun nestedApi() {
        NetManager.getApiService().getInTheatresApi("InTheaters", key)
            .enqueue(object : Callback<imdbApiModel<List<NestedModel.InTheatresModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<NestedModel.InTheatresModel>>>,
                    response: Response<imdbApiModel<List<NestedModel.InTheatresModel>>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("malumot","${response.body()}")

                        viewModel.nestedList.value = response.body()!!.items
                    }
                }
                override fun onFailure(
                    call: Call<imdbApiModel<List<NestedModel.InTheatresModel>>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        requireContext(),
                        "Check internet connection ;)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
    }

    private fun initLivedata() {
        viewModel.netList.observe(viewLifecycleOwner, Observer {

            binding.swipeContainer.isRefreshing = false
            //slider popular movies
            binding.popularMSlider.apply {
                setSliderAdapter(SliderMPM_Adapter(it.subList(0, 7)))
                setIndicatorAnimation(IndicatorAnimationType.DROP)
                setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
                scrollTimeInSec = 3
                autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
                startAutoCycle()
            }
        })

        viewModel.nestedList.observe(viewLifecycleOwner, Observer {
            binding.nestedRecycHome.apply {
                adapter = MainNestedAdapter(it)
            }
        })
    }
}

//        NetManager.getApiService().getApi_topM("MostPopularMovies", key).enqueue(this)
//        NetManager.getApiService().getApi_topM("InTheaters", key).enqueue(this)