package com.hfad.bookcollectionmanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.hfad.bookcollectionmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * View binding setup
         * */
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root

        /**
         * Set action bar
         * */
        setSupportActionBar(binding.toolbarPrimary)

        /**Configure toolbar to have back button and to show which screen is being displayed*/

        /**
         * Get a reference to the navigation controller from the nav host fragment
         */
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController


        /**
         * Link toolbar to nav graph
         */
        val builder = AppBarConfiguration.Builder(navController.graph)
        val appBarConfiguration = builder.build()
        /**
         * Apply config to toolbar
         * */
        binding.toolbarPrimary.setupWithNavController(navController, appBarConfiguration)
        setContentView(view)
    }

    /**
     * Add menu items to toolbar
     * */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Navigate to destination when clicked
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)
    }

}
