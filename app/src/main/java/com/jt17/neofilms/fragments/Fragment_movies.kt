package com.jt17.neofilms.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.MoviesAdapter
//import com.jt17.neofilms.adapters.ItemCallback
import com.jt17.neofilms.databinding.FragmentMoviesBinding
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.jt17.neofilms.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_movies : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val topMAdapter by lazy { MoviesAdapter() }

    lateinit var viewModel: BaseViewModel

    private lateinit var baseViewModel: BaseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[BaseViewModel::class.java]

        initRecyc()

        binding.moviesShimm.startShimmer()

        loadApi()
//        initLiveData()
    }

    private fun loadApi() {
        NetManager.getApiService().getApi_topM("Top250Movies", "k_jo0bfe4d").enqueue(object : Callback<imdbApiModel<List<mainModel>>> {
            override fun onResponse(
                call: Call<imdbApiModel<List<mainModel>>>,
                response: Response<imdbApiModel<List<mainModel>>>
            ) {
                if (response.isSuccessful) {
//            Log.d("GetApiData", "${response.body()}")

                    try {
                        binding.moviesShimm.stopShimmer()
                        binding.moviesShimm.visibility = View.GONE
                        binding.recycMovies.visibility = View.VISIBLE
                        topMAdapter.TopMlist(response.body()!!.items)
                    } catch (e: Exception) {

                    }
                }
            }
            override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
                Toast.makeText(requireContext(), "Check internet connection ;)", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    private fun initLiveData() {
//        viewModel.netList.observe(viewLifecycleOwner, Observer {
//            binding.recycMovies.run {
//                topMAdapter.TopMlist(it)
//            }
//        })
//    }

    private fun initRecyc() {
        binding.recycMovies.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topMAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}