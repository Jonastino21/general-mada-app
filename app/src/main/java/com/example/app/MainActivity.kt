package com.example.app

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
        setupToolbar()
        configureStatusBar()

        // ✅ Nouveau : gestion du bouton retour avec OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this) {
            if (!navController.popBackStack()) {
                // Si aucune destination précédente, on ferme l'activité
                finish()
            }
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Définir les destinations principales (pas de flèche retour sur ces écrans)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.bookingsFragment,
                R.id.favoritesFragment,
                R.id.profileFragment
            )
        )

        findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
            setupWithNavController(navController)

            // Utiliser les icônes déjà colorées
            itemIconTintList = null

            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> navController.navigate(R.id.homeFragment)
                    R.id.bookingsFragment -> navController.navigate(R.id.bookingsFragment)
                    R.id.favoritesFragment -> navController.navigate(R.id.favoritesFragment)
                    R.id.profileFragment -> navController.navigate(R.id.profileFragment)
                }
                true
            }
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun configureStatusBar() {
        window.apply {
            statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.primary_dark)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun showNotificationBadge(count: Int) {
        findViewById<BottomNavigationView>(R.id.bottom_navigation).getOrCreateBadge(R.id.bookingsFragment).apply {
            backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.notification_red)
            number = count
            isVisible = count > 0
        }
    }
}
