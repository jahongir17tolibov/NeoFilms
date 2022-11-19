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
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.top250_moviesModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_movies : Fragment(), Callback<imdbApiModel<List<top250_moviesModel>>> {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().findViewById<RecyclerView>(R.id.recyc_movies).layoutManager = LinearLayoutManager(requireContext())

        NetManager.getApiService_topM().getApi_topM("k_jo0bfe4d").enqueue(this)

        Log.d("GetApiData", "Open Fragment_movies")

//        requireActivity().findViewById<RecyclerView>(R.id.recyc_movies).

    }

    override fun onResponse(
        call: Call<imdbApiModel<List<top250_moviesModel>>>,
        response: Response<imdbApiModel<List<top250_moviesModel>>>
    ) {
        if (response.isSuccessful) {
            Log.d("GetApiData", "${response.body()}")
            requireActivity().findViewById<RecyclerView>(R.id.recyc_movies).adapter = HomeAdapter(response.body()!!.items)
        }
    }

    override fun onFailure(call: Call<imdbApiModel<List<top250_moviesModel>>>, t: Throwable) {
        Toast.makeText(requireContext(), "Check internet connection ;)", Toast.LENGTH_SHORT).show()
    }
}