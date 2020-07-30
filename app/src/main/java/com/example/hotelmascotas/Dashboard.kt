package com.example.hotelmascotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hotelmascotas.fragments.HomeFragment
import com.example.hotelmascotas.fragments.MenuFragment
import com.example.hotelmascotas.fragments.MisMacotasFragment
import kotlinx.android.synthetic.main.activity_dashboard.*

class Dashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val homeFragment = HomeFragment()
        val menuFragment = MenuFragment()
        val misMascotasFragment = MisMacotasFragment()

        makeCurrentFragment(homeFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigationHome -> makeCurrentFragment(homeFragment)
                R.id.navigationMascotas -> makeCurrentFragment(misMascotasFragment)
                R.id.navigationMenu -> makeCurrentFragment(menuFragment)
            }
            true
        }
    }


    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }


}
