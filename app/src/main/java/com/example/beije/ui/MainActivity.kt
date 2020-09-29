package com.example.beije.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.beije.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()

    }

    private fun setupViews()
    {
        // Finding the Navigation Controller
        val navController = findNavController(R.id.nav_host_fragment)

        // Setting Navigation Controller with the BottomNavigationView
        nav_view.setupWithNavController(navController)

        // Pass the IDs of top-level destinations in AppBarConfiguration
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf (
                R.id.navigation_master,
                R.id.navigation_detail
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
    }
}
