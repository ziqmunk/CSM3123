package com.example.donationapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.donationapp.R
import com.example.donationapp.ui.fragments.LoginFragment
import com.example.donationapp.ui.fragments.RegisterFragment
import com.google.android.material.tabs.TabLayout

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        // Add tabs
        tabLayout.addTab(tabLayout.newTab().setText("Login"))
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"))

        // Load the first fragment (LoginFragment) by default
        if (savedInstanceState == null) {
            loadFragment(LoginFragment())
        }

        // Handle tab selection
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> LoginFragment()
                    1 -> RegisterFragment()
                    else -> throw IllegalStateException("Unexpected tab position")
                }
                loadFragment(fragment)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
