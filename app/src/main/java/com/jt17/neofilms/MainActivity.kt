package com.jt17.neofilms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.jt17.neofilms.ApiServices.NetManager
import com.jt17.neofilms.databinding.ActivityMainBinding
import com.jt17.neofilms.fragments.Fragment_home
import com.jt17.neofilms.fragments.Fragment_movies
import com.jt17.neofilms.fragments.Fragment_shows
import com.jt17.neofilms.models.InTheatresModel
import com.jt17.neofilms.models.imdbApiModel
import com.jt17.neofilms.models.mainModel
import com.jt17.neofilms.viewmodel.BaseViewModel
import com.orhanobut.hawk.Hawk
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var viewmpdel: BaseViewModel
    private val key = "k_jo0bfe4d"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewmpdel = ViewModelProvider(this)[BaseViewModel::class.java]

        Hawk.init(this).build()

        //default main = fragment home();
        change_replacedFragment(Fragment_home())

        //if navigation buttons clicked use for migrate by fragments
        fragment_click()

        //load api for swiperefersh
        loadApi()
        nestedApi()

    }

    private fun loadApi() {

        NetManager.getApiService().getApi_topM("MostPopularMovies", key)
            .enqueue(object : Callback<imdbApiModel<List<mainModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<mainModel>>>,
                    response: Response<imdbApiModel<List<mainModel>>>
                ) {
                    if (response.isSuccessful) {
                        viewmpdel.netList.value = response.body()!!.items
                    }
                }

                override fun onFailure(call: Call<imdbApiModel<List<mainModel>>>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Check internet connection ;)",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    private fun nestedApi() {

        NetManager.getApiService().getInTheatresApi("InTheaters", key)
            .enqueue(object : Callback<imdbApiModel<List<InTheatresModel>>> {
                override fun onResponse(
                    call: Call<imdbApiModel<List<InTheatresModel>>>,
                    response: Response<imdbApiModel<List<InTheatresModel>>>
                ) {
                    if (response.isSuccessful) {
                        Log.d("dvhshdh","${response.body()}")

                        viewmpdel.nestedList.value = response.body()!!.items
                    }
                }

                override fun onFailure(
                    call: Call<imdbApiModel<List<InTheatresModel>>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        this@MainActivity,
                        "Check internet connection ;)",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })
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

    //navigation clicklisteners
    private fun fragment_click() {
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
    }
}