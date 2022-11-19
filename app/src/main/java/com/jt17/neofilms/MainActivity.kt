package com.jt17.neofilms

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

        val topm = findViewById<LottieAnimationView>(R.id.lottie_mv)
        val animated_icon = findViewById<LottieAnimationView>(R.id.lottie_tvS)


        topm.playAnimation()
        topm.postDelayed({
            changeFragment(Fragment_movies())
            topm.pauseAnimation()
        }, 1000)

        animated_icon.playAnimation()
        animated_icon.postDelayed({
            changeFragment(Fragment_shows())
            animated_icon.pauseAnimation()
        }, 1000)

        topm.setOnClickListener {
            topm.playAnimation()
            topm.postDelayed({
                changeFragment(Fragment_movies())
                topm.pauseAnimation()
            }, 1000)
        }

        animated_icon.setOnClickListener {
            animated_icon.playAnimation()
            animated_icon.postDelayed({
                changeFragment(Fragment_shows())
                animated_icon.pauseAnimation()
            }, 2000)
        }

    }

    private fun changeFragment(fragment: Fragment) {
        //use add function
        val fragmentmanager = supportFragmentManager
        val fragmentTransaction = fragmentmanager.beginTransaction()
        fragmentTransaction
            .add(R.id.mainFragment, fragment, "TAG")
            .addToBackStack("TAG")
        fragmentTransaction.commit()
    }
}