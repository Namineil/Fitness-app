package com.example.gym.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gym.R
import User
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.gym.setupWithNavController


class HomeActivity : AppCompatActivity() {

    private lateinit var user: User

    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userJson = intent.getStringExtra("User");
        val gson = GsonBuilder().create()
        user = gson.fromJson(userJson, User::class.java);

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(R.navigation.home, R.navigation.schedule, R.navigation.my_notes, R.navigation.profile)
        bottomNavigationView.selectedItemId = R.id.home

        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(navController)
        })
        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

}