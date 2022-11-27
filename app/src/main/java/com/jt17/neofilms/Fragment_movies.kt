package com.jt17.neofilms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.HomeAdapter
import com.jt17.neofilms.adapters.ItemCallback
import com.jt17.neofilms.databinding.FragmentMoviesBinding
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.jt17.neofilms.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_movies : Fragment(), Callback<imdbApiModel<List<mainModel>>> {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel2: BaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        viewModel2 = ViewModelProvider(requireActivity())[BaseViewModel::class.java]
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycMovies.layoutManager = LinearLayoutManager(requireContext())

//        val anim: SpringAnimation = SpringAnimation(binding.recycMovies, SpringAnimation.TRANSLATION_Y)
//            .setSpring(SpringForce()
//                .setFinalPosition(0f)
//                .setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY)
//                .setStiffness(SpringForce.STIFFNESS_LOW)
//            )

        binding.moviesShimm.startShimmer()

        NetManager.getApiService().getApi_topM("Top250Movies", "k_jo0bfe4d").enqueue(this)


    }

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
                binding.recycMovies.adapter = HomeAdapter(response.body()!!.items, object : ItemCallback{
                    override fun itemClickListener(position: Int) {
                        viewModel2.txt.value = "AAAAAAAADDDDDDDDDDDDD"
                    }
                })
            } catch (e: Exception) {

            }
        }
    }

    override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
        Toast.makeText(requireContext(), "Check internet connection ;)", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}