package com.jt17.neofilms.fragments

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
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.ModalBottomSheet
import com.jt17.neofilms.adapters.InTheatersAdapter
import com.jt17.neofilms.adapters.ItemCallback
import com.jt17.neofilms.adapters.SliderMPM_Adapter
import com.jt17.neofilms.databinding.FragmentHomeBinding
import com.jt17.neofilms.db.BaseUtils
import com.jt17.neofilms.models.HomeModel
import com.jt17.neofilms.models.InTheatresModel
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
    private val key = "k_jo0bfe4d"

    //    private val subSliderAdapter by lazy { SliderMPM_Adapter() }
    private val subInThAdapeter by lazy {
        InTheatersAdapter(object : ItemCallback {
            override fun putBottomSheet(itemData: InTheatresModel) {
                val modalBottomSheet = ModalBottomSheet(itemData)
                modalBottomSheet.show(
                    requireActivity().supportFragmentManager,
                    ModalBottomSheet.TAG
                )
            }

        })
    }

//    private val inThAdapter by lazy {
//        InTheatersAdapter()
//        //        .onItemclick = {
////            val modalBottomSheet = ModalBottomSheet(it)
////            modalBottomSheet.show(
////                requireActivity().supportFragmentManager,
////                ModalBottomSheet.TAG
////            )
////        }
//
//    }

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

//        binding.swipeContainer.isRefreshing = true
        initRecyc()

        binding.swipeContainer.setOnRefreshListener {
            popularMoviesApi()
            theatersApi()
        }

        initLivedata()

    }

    private fun popularMoviesApi() {
        NetManager.getApiService().getApi_topM("MostPopularMovies", key)
            .enqueue(object : Callback<imdbApiModel<List<mainModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<mainModel>>>,
                    response: Response<imdbApiModel<List<mainModel>>>
                ) {
                    try {
                        if (response.isSuccessful) {
                            val responseList = response.body()!!.items
                            if (response.body()!!.items.isNotEmpty()) {
                                BaseUtils.setApiMP(response.body()!!.items)
                            }
                            viewModel.netList.value = response.body()!!.items
                        }
                    } catch (_: Exception) {
                    }

                }

                override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "Check internet connection ;)",
                        Toast.LENGTH_SHORT
                    ).show()

                    viewModel.netList.value = BaseUtils.getApiMP()
                }
            })
    }

    private fun theatersApi() {
        NetManager.getApiService().getInTheatresApi("InTheaters", key)
            .enqueue(object : Callback<imdbApiModel<List<InTheatresModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<InTheatresModel>>>,
                    response: Response<imdbApiModel<List<InTheatresModel>>>
                ) {
                    try {
                        if (response.isSuccessful) {
//                        Log.d("logger", response.body()!!.items.toString())
                            if (response.body()!!.items.isNotEmpty()) {
                                BaseUtils.setApiList(response.body()!!.items)
                            }
                            viewModel.nestedList.value = response.body()!!.items
                        }
                    } catch (_: Exception) {
                    }
                }

                override fun onFailure(
                    call: Call<imdbApiModel<List<InTheatresModel>>>,
                    t: Throwable
                ) {

                    viewModel.nestedList.value = BaseUtils.getApiList()

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

//            slider popular movies
            binding.popularMSlider.run {
                setSliderAdapter(SliderMPM_Adapter(it.subList(0, 12).shuffled()))
                setIndicatorAnimation(IndicatorAnimationType.THIN_WORM)
                setSliderTransformAnimation(SliderAnimations.VERTICALSHUTTRANSFORMATION)
                scrollTimeInSec = 3
                autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
                startAutoCycle()
            }
        })

        viewModel.nestedList.observe(viewLifecycleOwner, Observer {
//            binding.swipeContainer.isRefreshing = false
            subInThAdapeter.newList(it)
        })
    }

    private fun initRecyc() {
        binding.nestedRecycHome.run {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = subInThAdapeter
        }

    }

//    private fun BottomShCallBack() {
//        binding.nestedRecycHome.adapter = NestedAdapter(listOf(), object : ite {
//
//        })
//    }

}
//        NetManager.getApiService().getApi_topM("MostPopularMovies", key).enqueue(this)
//
//}
//        NetManager.getApiService().getApi_topM("InTheaters", key).enqueue(this)