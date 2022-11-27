package com.jt17.neofilms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.ShowsAdapter
import com.jt17.neofilms.databinding.FragmentShowsBinding
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Fragment_shows : Fragment(), Callback<imdbApiModel<List<mainModel>>> {
    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycShows.layoutManager = LinearLayoutManager(requireContext())

        binding.showsShimm.startShimmer()

        NetManager.getApiService().getApi_topM("Top250TVs", "k_jo0bfe4d").enqueue(this)

    }

    override fun onResponse(
        call: Call<imdbApiModel<List<mainModel>>>,
        response: Response<imdbApiModel<List<mainModel>>>
    ) {
        if (response.isSuccessful) {
            try {
                binding.showsShimm.stopShimmer()
                binding.showsShimm.visibility = View.GONE
                binding.recycShows.visibility = View.VISIBLE
                binding.recycShows.adapter = ShowsAdapter(response.body()!!.items)
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