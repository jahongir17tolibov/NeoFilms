package com.jt17.neofilms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.airbnb.lottie.LottieAnimationView
import com.jt17.neofilms.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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