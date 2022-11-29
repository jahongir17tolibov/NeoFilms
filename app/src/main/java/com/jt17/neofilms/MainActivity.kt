package com.jt17.neofilms

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.airbnb.lottie.LottieAnimationView
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.adapters.MainNestedAdapter
import com.jt17.neofilms.adapters.SliderMPM_Adapter
import com.jt17.neofilms.databinding.ActivityMainBinding
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


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
   lateinit var viewmpdel: BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmpdel = ViewModelProvider(this)[BaseViewModel::class.java]

        change_replacedFragment(Fragment_home())

        binding.bottomNavigate.setOnItemSelectedListener {
            when (it.itemId) {
                //home category changed
                R.id.home_btm -> change_replacedFragment(Fragment_home())
                //movies category changed
                R.id.movies_btm -> change_replacedFragment(Fragment_movies())
                //shows category changed
                R.id.shows_btm -> change_replacedFragment(Fragment_shows())
            }
            true
        }
        loadApi()

    }
    private fun loadApi(){

         val key = "k_jo0bfe4d"

        NetManager.getApiService().getApi_topM("InTheaters", key).enqueue(object : Callback<imdbApiModel<List<mainModel>>>{
            override fun onResponse(
                call: Call<imdbApiModel<List<mainModel>>>,
                response: Response<imdbApiModel<List<mainModel>>>
            ) {
                if(response.isSuccessful){
                    viewmpdel.netList.value = response.body()!!.items
                }
            }

            override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {

            }
        })

//        @SuppressLint("ResourceType")
//        override fun onResponse(
//            call: Call<imdbApiModel<List<mainModel>>>,
//            response: Response<imdbApiModel<List<mainModel>>>
//        ) {
//            //slider popular movies
//            binding.popularMSlider.apply {
//                setSliderAdapter(SliderMPM_Adapter(response.body()!!.items.subList(0, 5)))
//                setIndicatorAnimation(IndicatorAnimationType.DROP)
//                setSliderTransformAnimation(SliderAnimations.CUBEINSCALINGTRANSFORMATION)
//                scrollTimeInSec = 3
//                autoCycleDirection = SliderView.AUTO_CYCLE_DIRECTION_RIGHT
//                startAutoCycle()
//            }
//            mylist.add(NestedModel("In Theatres", response.body()!!.items.shuffled().subList(0, 6)))
//            mylist.add(NestedModel("In Top", response.body()!!.items.shuffled().subList(0, 10)))
//            mylist.add(NestedModel("In Top", response.body()!!.items.reversed().subList(0, 10)))
//
//            binding.nestedRecycHome.apply {
//                adapter = MainNestedAdapter(mylist)
//            }
//        }
//
//        override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
//            Toast.makeText(requireContext(), "Check internet connection ;)", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun change_replacedFragment(fragment: Fragment) {
        //use replace function
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mainFragment, fragment)
        fragmentTransaction.commit()

//        //use add function
//        val fragmentmanager = supportFragmentManager
//        val fragmentTransaction = fragmentmanager.beginTransaction()
//        fragmentTransaction
//            .add(R.id.mainFragment, fragment, "TAG")
//            .addToBackStack("TAG")
//        fragmentTransaction.commit()
    }
}