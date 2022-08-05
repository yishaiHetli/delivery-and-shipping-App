package com.example.projectlogin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.projectlogin.fragments.Fragment1
import com.example.projectlogin.fragments.Fragment2
import com.example.projectlogin.fragments.Fragment3
import com.example.projectlogin.R
import com.google.android.material.navigation.NavigationView

class ProfileActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawer: DrawerLayout
    private var email = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val extras = intent.extras
        if (extras != null) {
            email = extras.getString("email").toString()
        }
        drawer = findViewById(R.id.drawer_layout)
        val toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        findViewById<NavigationView>(R.id.nv_view).setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nv_first_drawer -> {
                setToolBarTitle("Nearby delivery")
                replaceFragment(Fragment1())
            }
            R.id.nv_second_drawer -> {
                setToolBarTitle("Need to deliver")
                replaceFragment(Fragment2())
            }
            R.id.nv_third_drawer -> {
                setToolBarTitle("Delivered")
                replaceFragment(Fragment3())
            }
        }
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        val bundle = Bundle()
        bundle.putString("email",email)
        fragment.arguments = bundle
        val fragmentManger = supportFragmentManager
        val fragmentTransition = fragmentManger.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, fragment).commit()
    }

    private fun setToolBarTitle(title: String) {
        supportActionBar?.title = title
    }
}