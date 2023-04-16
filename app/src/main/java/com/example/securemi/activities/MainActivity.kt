package com.example.securemi.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import com.example.securemi.R
import com.example.securemi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController=navHostFragment!!.findNavController()
        val popupMenu= PopupMenu(this,null)
        popupMenu.inflate(R.menu.bottomnavbarmenu)

        binding.bottomBar.setupWithNavController(popupMenu.menu,navController)
//        navController.addOnDestinationChangedListener { controller, destination, arguments ->
//            title = when (destination.id) {
//                R.id.homeFragment2 -> "Home"
//                R.id.helplineFragment -> "helpline"
//                R.id.notificationFragment -> "notification"
//                R.id.userFragment -> "user"
//                else -> "pkart"
//            }
//        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.statusmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.notif -> {
                Toast.makeText(this, "notif/....", Toast.LENGTH_SHORT).show()
            }
            R.id.hom -> {
                Toast.makeText(this, "ho/....", Toast.LENGTH_SHORT).show()


            }
        }
        return super.onOptionsItemSelected(item)
    }
}