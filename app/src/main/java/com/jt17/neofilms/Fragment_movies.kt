package com.jt17.neofilms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.HomeAdapter
import com.jt17.neofilms.databinding.FragmentMoviesBinding
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.top250_moviesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_movies : Fragment(), Callback<imdbApiModel<List<top250_moviesModel>>> {

    private var _binding: FragmentMoviesBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycMovies.layoutManager = LinearLayoutManager(requireContext())

        NetManager.getApiService_topM().getApi_topM("k_jo0bfe4d").enqueue(this)


    }

    override fun onResponse(
        call: Call<imdbApiModel<List<top250_moviesModel>>>,
        response: Response<imdbApiModel<List<top250_moviesModel>>>
    ) {
        if (response.isSuccessful) {
//            Log.d("GetApiData", "${response.body()}")
            binding.recycMovies.adapter = HomeAdapter(response.body()!!.items)
        }
    }

    override fun onFailure(call: Call<imdbApiModel<List<top250_moviesModel>>>, t: Throwable) {
        Toast.makeText(requireContext(), "Check internet connection ;)", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}